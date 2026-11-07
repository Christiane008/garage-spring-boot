package com.garage.garage.mapper;

import com.garage.garage.dto.ClientDTO;
import com.garage.garage.dto.ClientFormDTO;
import com.garage.garage.entity.Client;
import org.springframework.stereotype.Component;

// Conversion Entity <-> DTO pour Client. Ne touche jamais au Repository :
// c'est au Service de récupérer/persister, au Mapper de convertir.
@Component
public class ClientMapper {

    public ClientDTO toDto(Client client) {
        return new ClientDTO(
                client.getId(),
                client.getNom(),
                client.getPrenom(),
                client.getEmail(),
                client.getTelephone()
        );
    }

    public Client toEntity(ClientFormDTO form) {
        Client client = new Client();
        updateEntity(client, form);
        return client;
    }

    // Met à jour une entité existante à partir d'un formulaire (cas modification)
    public void updateEntity(Client client, ClientFormDTO form) {
        client.setNom(form.getNom());
        client.setPrenom(form.getPrenom());
        client.setEmail(form.getEmail());
        client.setTelephone(form.getTelephone());
    }
}
