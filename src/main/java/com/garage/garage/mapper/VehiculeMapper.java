package com.garage.garage.mapper;

import com.garage.garage.dto.VehiculeDTO;
import com.garage.garage.dto.VehiculeFormDTO;
import com.garage.garage.entity.Vehicule;
import org.springframework.stereotype.Component;

@Component
public class VehiculeMapper {

    public VehiculeDTO toDto(Vehicule vehicule) {
        return new VehiculeDTO(
                vehicule.getId(),
                vehicule.getImmatriculation(),
                vehicule.getMarque(),
                vehicule.getModele(),
                vehicule.getAnnee(),
                vehicule.getKilometrage(),
                vehicule.getProprietaire().getId(),
                vehicule.getProprietaire().getNom()
        );
    }

    // Ne renseigne PAS le propriétaire : résoudre le Client via ClientRepository
    // reste la responsabilité du Service, jamais du Mapper.
    public Vehicule toEntity(VehiculeFormDTO form) {
        Vehicule vehicule = new Vehicule();
        updateEntity(vehicule, form);
        return vehicule;
    }

    public void updateEntity(Vehicule vehicule, VehiculeFormDTO form) {
        vehicule.setImmatriculation(form.getImmatriculation());
        vehicule.setMarque(form.getMarque());
        vehicule.setModele(form.getModele());
        vehicule.setAnnee(form.getAnnee());
        vehicule.setKilometrage(form.getKilometrage());
    }
}
