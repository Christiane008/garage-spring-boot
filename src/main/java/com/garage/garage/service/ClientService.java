package com.garage.garage.service;

import com.garage.garage.dto.ClientDTO;
import com.garage.garage.dto.ClientFormDTO;
import com.garage.garage.entity.Client;
import com.garage.garage.exception.OperationNonAutoriseeException;
import com.garage.garage.exception.RessourceNonTrouveeException;
import com.garage.garage.mapper.ClientMapper;
import com.garage.garage.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    // --- Entités ---

    @Transactional(readOnly = true)
    public List<Client> trouverTous() {
        return clientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Client> rechercherParNom(String nom) {
        if (nom == null || nom.isBlank()) {
            return clientRepository.findAll();
        }
        return clientRepository.findByNomContainingIgnoreCase(nom);
    }

    @Transactional(readOnly = true)
    public Client trouverParId(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RessourceNonTrouveeException("Client introuvable avec l'id " + id));
    }

    public Client ajouter(ClientFormDTO form) {
        Client client = clientMapper.toEntity(form);
        log.info("Création du client {}", client.getEmail());
        return clientRepository.save(client);
    }

    public Client modifier(Long id, ClientFormDTO form) {
        Client client = trouverParId(id);
        clientMapper.updateEntity(client, form);
        return clientRepository.save(client);
    }

    public void supprimer(Long id) {
        Client client = trouverParId(id);
        if (!client.getVehicules().isEmpty()) {
            throw new OperationNonAutoriseeException(
                    "Impossible de supprimer un client qui possède encore des véhicules");
        }
        clientRepository.delete(client);
    }

    // --- DTOs (utilisés par les contrôleurs) ---

    @Transactional(readOnly = true)
    public List<ClientDTO> trouverTousDto() {
        return trouverTous().stream().map(clientMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClientDTO trouverParIdDto(Long id) {
        return clientMapper.toDto(trouverParId(id));
    }

    public ClientDTO ajouterDto(ClientFormDTO form) {
        return clientMapper.toDto(ajouter(form));
    }

    public ClientDTO modifierDto(Long id, ClientFormDTO form) {
        return clientMapper.toDto(modifier(id, form));
    }
}
