package com.garage.garage.service;

import com.garage.garage.dto.VehiculeDTO;
import com.garage.garage.dto.VehiculeFormDTO;
import com.garage.garage.entity.Client;
import com.garage.garage.entity.Vehicule;
import com.garage.garage.exception.KilometrageInvalideException;
import com.garage.garage.exception.RessourceNonTrouveeException;
import com.garage.garage.mapper.VehiculeMapper;
import com.garage.garage.repository.ClientRepository;
import com.garage.garage.repository.VehiculeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final ClientRepository clientRepository;
    private final VehiculeMapper vehiculeMapper;

    public VehiculeService(VehiculeRepository vehiculeRepository, ClientRepository clientRepository,
                            VehiculeMapper vehiculeMapper) {
        this.vehiculeRepository = vehiculeRepository;
        this.clientRepository = clientRepository;
        this.vehiculeMapper = vehiculeMapper;
    }

    @Transactional(readOnly = true)
    public List<Vehicule> trouverTous() {
        return vehiculeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Vehicule trouverParId(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new RessourceNonTrouveeException("Véhicule introuvable avec l'id " + id));
    }

    @Transactional(readOnly = true)
    public List<Vehicule> rechercher(String marque, String immatriculation) {
        return vehiculeRepository.rechercher(
                (marque == null || marque.isBlank()) ? null : marque,
                (immatriculation == null || immatriculation.isBlank()) ? null : immatriculation);
    }

    public Vehicule ajouter(VehiculeFormDTO form) {
        Client client = clientRepository.findById(form.getClientId())
                .orElseThrow(() -> new RessourceNonTrouveeException(
                        "Client introuvable avec l'id " + form.getClientId()));

        Vehicule vehicule = vehiculeMapper.toEntity(form);
        vehicule.setProprietaire(client);
        return vehiculeRepository.save(vehicule);
    }

    public Vehicule modifier(Long id, VehiculeFormDTO form) {
        Vehicule vehicule = trouverParId(id);

        // Règle métier : le kilométrage ne doit jamais diminuer lors d'une mise à jour
        if (form.getKilometrage() < vehicule.getKilometrage()) {
            throw new KilometrageInvalideException(
                    "Le nouveau kilométrage (" + form.getKilometrage() +
                    ") ne peut pas être inférieur à l'ancien (" + vehicule.getKilometrage() + ")");
        }

        if (!form.getClientId().equals(vehicule.getProprietaire().getId())) {
            Client client = clientRepository.findById(form.getClientId())
                    .orElseThrow(() -> new RessourceNonTrouveeException(
                            "Client introuvable avec l'id " + form.getClientId()));
            vehicule.setProprietaire(client);
        }

        vehiculeMapper.updateEntity(vehicule, form);
        return vehiculeRepository.save(vehicule);
    }

    public void supprimer(Long id) {
        Vehicule vehicule = trouverParId(id);
        vehiculeRepository.delete(vehicule);
    }

    // --- DTOs ---

    @Transactional(readOnly = true)
    public List<VehiculeDTO> trouverTousDto() {
        return trouverTous().stream().map(vehiculeMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VehiculeDTO trouverParIdDto(Long id) {
        return vehiculeMapper.toDto(trouverParId(id));
    }

    public VehiculeDTO ajouterDto(VehiculeFormDTO form) {
        return vehiculeMapper.toDto(ajouter(form));
    }

    public VehiculeDTO modifierDto(Long id, VehiculeFormDTO form) {
        return vehiculeMapper.toDto(modifier(id, form));
    }
}
