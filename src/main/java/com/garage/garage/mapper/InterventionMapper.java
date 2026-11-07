package com.garage.garage.mapper;

import com.garage.garage.dto.InterventionDTO;
import com.garage.garage.dto.InterventionEditFormDTO;
import com.garage.garage.dto.InterventionFormDTO;
import com.garage.garage.entity.Intervention;
import com.garage.garage.entity.StatutIntervention;
import org.springframework.stereotype.Component;

@Component
public class InterventionMapper {

    public InterventionDTO toDto(Intervention intervention) {
        return new InterventionDTO(
                intervention.getId(),
                intervention.getDescription(),
                intervention.getDateDebut(),
                intervention.getDateFin(),
                intervention.getStatut(),
                intervention.getCoutMainOeuvre(),
                intervention.getVehicule().getId(),
                intervention.getVehicule().getImmatriculation(),
                intervention.getFacture() != null ? intervention.getFacture().getId() : null
        );
    }

    // Ne renseigne PAS le véhicule : résoudre le Vehicule via VehiculeRepository
    // reste la responsabilité du Service.
    public Intervention toEntity(InterventionFormDTO form) {
        Intervention intervention = new Intervention();
        intervention.setDescription(form.getDescription());
        intervention.setDateDebut(form.getDateDebut());
        intervention.setCoutMainOeuvre(form.getCoutMainOeuvre());
        intervention.setStatut(StatutIntervention.EN_ATTENTE);
        return intervention;
    }

    public void updateEntity(Intervention intervention, InterventionEditFormDTO form) {
        intervention.setDescription(form.getDescription());
        intervention.setDateDebut(form.getDateDebut());
        intervention.setCoutMainOeuvre(form.getCoutMainOeuvre());
    }
}
