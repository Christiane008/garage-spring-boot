package com.garage.garage.service;

import com.garage.garage.dto.LigneInterventionDTO;
import com.garage.garage.dto.LigneInterventionFormDTO;
import com.garage.garage.entity.Intervention;
import com.garage.garage.entity.LigneIntervention;
import com.garage.garage.entity.PieceDetachee;
import com.garage.garage.entity.StatutIntervention;
import com.garage.garage.exception.OperationNonAutoriseeException;
import com.garage.garage.exception.RessourceNonTrouveeException;
import com.garage.garage.exception.StockInsuffisantException;
import com.garage.garage.mapper.LigneInterventionMapper;
import com.garage.garage.repository.InterventionRepository;
import com.garage.garage.repository.LigneInterventionRepository;
import com.garage.garage.repository.PieceDetacheeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// Logique métier de l'association Intervention <-> PieceDetachee (via LigneIntervention).
// Règles :
//  - on ne peut pas ajouter/retirer une pièce sur une intervention déjà TERMINEE ou ANNULEE
//  - ajouter une ligne décrémente le stock de la pièce ; refus si stock insuffisant
//  - le prix unitaire est figé au moment de l'ajout (snapshot), indépendant d'une
//    évolution future du tarif de la pièce
//  - retirer une ligne recrédite le stock
@Service
public class LigneInterventionService {

    private static final Logger log = LoggerFactory.getLogger(LigneInterventionService.class);

    private final LigneInterventionRepository ligneInterventionRepository;
    private final InterventionRepository interventionRepository;
    private final PieceDetacheeRepository pieceDetacheeRepository;
    private final LigneInterventionMapper ligneInterventionMapper;

    public LigneInterventionService(LigneInterventionRepository ligneInterventionRepository,
                                     InterventionRepository interventionRepository,
                                     PieceDetacheeRepository pieceDetacheeRepository,
                                     LigneInterventionMapper ligneInterventionMapper) {
        this.ligneInterventionRepository = ligneInterventionRepository;
        this.interventionRepository = interventionRepository;
        this.pieceDetacheeRepository = pieceDetacheeRepository;
        this.ligneInterventionMapper = ligneInterventionMapper;
    }

    @Transactional
    public LigneIntervention ajouter(Long interventionId, LigneInterventionFormDTO form) {
        Intervention intervention = interventionRepository.findById(interventionId)
                .orElseThrow(() -> new RessourceNonTrouveeException(
                        "Intervention introuvable avec l'id " + interventionId));

        verifierModifiable(intervention);

        PieceDetachee piece = pieceDetacheeRepository.findById(form.getPieceId())
                .orElseThrow(() -> new RessourceNonTrouveeException(
                        "Pièce introuvable avec l'id " + form.getPieceId()));

        if (piece.getQuantiteStock() < form.getQuantite()) {
            throw new StockInsuffisantException(
                    "Stock insuffisant pour \"" + piece.getNom() + "\" : " +
                    piece.getQuantiteStock() + " disponible(s), " + form.getQuantite() + " demandé(s)");
        }

        LigneIntervention ligne = new LigneIntervention();
        ligne.setQuantite(form.getQuantite());
        ligne.setPrixUnitaire(piece.getPrixUnitaire()); // snapshot du prix au moment de l'utilisation
        ligne.setIntervention(intervention);
        ligne.setPiece(piece);

        piece.setQuantiteStock(piece.getQuantiteStock() - form.getQuantite());
        pieceDetacheeRepository.save(piece);

        LigneIntervention sauvegardee = ligneInterventionRepository.save(ligne);
        log.info("Ajout de {} x {} sur l'intervention {}", form.getQuantite(), piece.getNom(), interventionId);
        return sauvegardee;
    }

    @Transactional
    public void supprimer(Long interventionId, Long ligneId) {
        LigneIntervention ligne = ligneInterventionRepository.findById(ligneId)
                .orElseThrow(() -> new RessourceNonTrouveeException("Ligne introuvable avec l'id " + ligneId));

        if (!ligne.getIntervention().getId().equals(interventionId)) {
            throw new RessourceNonTrouveeException(
                    "Cette ligne n'appartient pas à l'intervention " + interventionId);
        }

        verifierModifiable(ligne.getIntervention());

        PieceDetachee piece = ligne.getPiece();
        piece.setQuantiteStock(piece.getQuantiteStock() + ligne.getQuantite());
        pieceDetacheeRepository.save(piece);

        ligneInterventionRepository.delete(ligne);
        log.info("Suppression de la ligne {} sur l'intervention {}", ligneId, interventionId);
    }

    @Transactional(readOnly = true)
    public List<LigneIntervention> trouverParIntervention(Long interventionId) {
        return ligneInterventionRepository.findByInterventionId(interventionId);
    }

    private void verifierModifiable(Intervention intervention) {
        if (intervention.getStatut() == StatutIntervention.TERMINEE
                || intervention.getStatut() == StatutIntervention.ANNULEE) {
            throw new OperationNonAutoriseeException(
                    "Impossible de modifier les pièces d'une intervention déjà " + intervention.getStatut());
        }
    }

    // --- DTOs ---

    public LigneInterventionDTO ajouterDto(Long interventionId, LigneInterventionFormDTO form) {
        return ligneInterventionMapper.toDto(ajouter(interventionId, form));
    }

    @Transactional(readOnly = true)
    public List<LigneInterventionDTO> trouverParInterventionDto(Long interventionId) {
        return trouverParIntervention(interventionId).stream()
                .map(ligneInterventionMapper::toDto)
                .collect(Collectors.toList());
    }
}
