package com.garage.garage.mapper;

import com.garage.garage.dto.LigneInterventionDTO;
import com.garage.garage.entity.LigneIntervention;
import org.springframework.stereotype.Component;

@Component
public class LigneInterventionMapper {

    public LigneInterventionDTO toDto(LigneIntervention ligne) {
        return new LigneInterventionDTO(
                ligne.getId(),
                ligne.getQuantite(),
                ligne.getPrixUnitaire(),
                ligne.getIntervention().getId(),
                ligne.getPiece().getId(),
                ligne.getPiece().getNom()
        );
    }
}
