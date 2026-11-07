package com.garage.garage.service;

import com.garage.garage.dto.InterventionDTO;
import com.garage.garage.dto.InterventionEditFormDTO;
import com.garage.garage.dto.InterventionFormDTO;
import com.garage.garage.entity.Intervention;
import com.garage.garage.entity.StatutIntervention;
import com.garage.garage.entity.Vehicule;
import com.garage.garage.exception.OperationNonAutoriseeException;
import com.garage.garage.exception.RessourceNonTrouveeException;
import com.garage.garage.mapper.InterventionMapper;
import com.garage.garage.repository.InterventionRepository;
import com.garage.garage.repository.VehiculeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterventionService {

    private static final Logger log = LoggerFactory.getLogger(InterventionService.class);

    private final InterventionRepository interventionRepository;
    private final VehiculeRepository vehiculeRepository;
    private final FactureService factureService;
    private final InterventionMapper interventionMapper;

    public InterventionService(InterventionRepository interventionRepository,
                                VehiculeRepository vehiculeRepository,
                                FactureService factureService,
                                InterventionMapper interventionMapper) {
        this.interventionRepository = interventionRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.factureService = factureService;
        this.interventionMapper = interventionMapper;
    }

    @Transactional(readOnly = true)
    public List<Intervention> trouverTous() {
        return interventionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Intervention trouverParId(Long id) {
        return interventionRepository.findById(id)
                .orElseThrow(() -> new RessourceNonTrouveeException("Intervention introuvable avec l'id " + id));
    }

    @Transactional(readOnly = true)
    public Page<Intervention> rechercher(StatutIntervention statut, LocalDate debut, LocalDate fin, Pageable pageable) {
        return interventionRepository.rechercher(statut, debut, fin, pageable);
    }

    public Intervention ajouter(InterventionFormDTO form) {
        Vehicule vehicule = vehiculeRepository.findById(form.getVehiculeId())
                .orElseThrow(() -> new RessourceNonTrouveeException(
                        "Véhicule introuvable avec l'id " + form.getVehiculeId()));

        Intervention intervention = interventionMapper.toEntity(form);
        intervention.setVehicule(vehicule);
        return interventionRepository.save(intervention);
    }

    // Modification des informations de base d'une intervention (description, date
    // de début, coût de main d'oeuvre) : uniquement possible tant qu'elle n'est
    // pas encore clôturée, pour ne jamais modifier une intervention déjà facturée.
    public Intervention modifier(Long id, InterventionEditFormDTO form) {
        Intervention intervention = trouverParId(id);

        if (intervention.getStatut() == StatutIntervention.TERMINEE
                || intervention.getStatut() == StatutIntervention.ANNULEE) {
            throw new OperationNonAutoriseeException(
                    "Impossible de modifier une intervention déjà " + intervention.getStatut());
        }

        interventionMapper.updateEntity(intervention, form);
        return interventionRepository.save(intervention);
    }

    // Clôture d'une intervention : passe le statut à TERMINEE, fixe la date de fin
    // et génère automatiquement la facture associée. Le tout doit réussir ou échouer
    // ensemble (@Transactional) pour ne jamais se retrouver avec une intervention
    // "terminée" sans facture.
    @Transactional
    public Intervention cloturer(Long id) {
        log.info("Clôture de l'intervention {}", id);
        Intervention intervention = trouverParId(id);

        if (intervention.getStatut() == StatutIntervention.TERMINEE
                || intervention.getStatut() == StatutIntervention.ANNULEE) {
            log.warn("Tentative de clôture d'une intervention déjà terminée/annulée : {}", id);
            throw new OperationNonAutoriseeException(
                    "Impossible de clôturer une intervention déjà " + intervention.getStatut());
        }

        intervention.setStatut(StatutIntervention.TERMINEE);
        intervention.setDateFin(LocalDate.now());
        Intervention sauvegardee = interventionRepository.save(intervention);

        factureService.genererPour(sauvegardee);

        log.info("Intervention {} clôturée avec succès", id);
        return sauvegardee;
    }

    public void supprimer(Long id) {
        Intervention intervention = trouverParId(id);
        interventionRepository.delete(intervention);
    }

    // --- DTOs ---

    @Transactional(readOnly = true)
    public List<InterventionDTO> trouverTousDto() {
        return trouverTous().stream().map(interventionMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InterventionDTO trouverParIdDto(Long id) {
        return interventionMapper.toDto(trouverParId(id));
    }

    public InterventionDTO ajouterDto(InterventionFormDTO form) {
        return interventionMapper.toDto(ajouter(form));
    }

    public InterventionDTO modifierDto(Long id, InterventionEditFormDTO form) {
        return interventionMapper.toDto(modifier(id, form));
    }

    public InterventionDTO cloturerDto(Long id) {
        return interventionMapper.toDto(cloturer(id));
    }
}
