package com.garage.garage.mapper;

import com.garage.garage.dto.FactureDTO;
import com.garage.garage.entity.Facture;
import org.springframework.stereotype.Component;

// Pas de méthode toEntity() : une Facture n'est jamais créée depuis un formulaire,
// uniquement générée automatiquement par FactureService.genererPour().
@Component
public class FactureMapper {

    public FactureDTO toDto(Facture facture) {
        return new FactureDTO(
                facture.getId(),
                facture.getNumero(),
                facture.getDateEmission(),
                facture.getMontantTotal(),
                facture.getStatut(),
                facture.getClient().getId(),
                facture.getClient().getNom(),
                facture.getIntervention() != null ? facture.getIntervention().getId() : null
        );
    }
}
