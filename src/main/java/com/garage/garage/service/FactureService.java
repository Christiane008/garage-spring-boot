package com.garage.garage.service;

import com.garage.garage.dto.FactureDTO;
import com.garage.garage.entity.Facture;
import com.garage.garage.entity.Intervention;
import com.garage.garage.entity.StatutFacture;
import com.garage.garage.exception.RessourceNonTrouveeException;
import com.garage.garage.mapper.FactureMapper;
import com.garage.garage.repository.FactureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FactureService {

    private final FactureRepository factureRepository;
    private final FactureMapper factureMapper;

    public FactureService(FactureRepository factureRepository, FactureMapper factureMapper) {
        this.factureRepository = factureRepository;
        this.factureMapper = factureMapper;
    }

    @Transactional(readOnly = true)
    public List<Facture> trouverTous() {
        return factureRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Facture trouverParId(Long id) {
        return factureRepository.findById(id)
                .orElseThrow(() -> new RessourceNonTrouveeException("Facture introuvable avec l'id " + id));
    }

    @Transactional(readOnly = true)
    public List<Facture> trouverParClient(Long clientId) {
        return factureRepository.findByClientId(clientId);
    }

    @Transactional
    public Facture genererPour(Intervention intervention) {
        BigDecimal totalPieces = intervention.getLignesIntervention().stream()
                .map(ligne -> ligne.getPrixUnitaire().multiply(BigDecimal.valueOf(ligne.getQuantite())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal montantTotal = totalPieces.add(intervention.getCoutMainOeuvre());

        Facture facture = new Facture();
        facture.setNumero("FAC-" + System.currentTimeMillis());
        facture.setDateEmission(LocalDate.now());
        facture.setMontantTotal(montantTotal);
        facture.setStatut(StatutFacture.EN_ATTENTE);
        facture.setClient(intervention.getVehicule().getProprietaire());
        facture.setIntervention(intervention);
        Facture sauvegardee = factureRepository.save(facture);

        // Synchronise le côté inverse (Intervention.facture) pour que l'objet en
        // mémoire reflète immédiatement la relation, sans attendre un rechargement.
        intervention.setFacture(sauvegardee);

        return sauvegardee;
    }

    // --- DTOs ---

    @Transactional(readOnly = true)
    public List<FactureDTO> trouverTousDto() {
        return trouverTous().stream().map(factureMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FactureDTO trouverParIdDto(Long id) {
        return factureMapper.toDto(trouverParId(id));
    }
}
