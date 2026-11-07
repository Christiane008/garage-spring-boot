package com.garage.garage.dto;

import com.garage.garage.entity.StatutIntervention;
import java.math.BigDecimal;
import java.time.LocalDate;

public class InterventionDTO {
    private Long id;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutIntervention statut;
    private BigDecimal coutMainOeuvre;
    private Long vehiculeId;
    private String vehiculeImmatriculation;
    private Long factureId; // renseigné uniquement une fois l'intervention clôturée et facturée

    public InterventionDTO() {
    }

    public InterventionDTO(Long id, String description, LocalDate dateDebut, LocalDate dateFin,
                            StatutIntervention statut, BigDecimal coutMainOeuvre, Long vehiculeId,
                            String vehiculeImmatriculation, Long factureId) {
        super();
        this.id = id;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
        this.coutMainOeuvre = coutMainOeuvre;
        this.vehiculeId = vehiculeId;
        this.vehiculeImmatriculation = vehiculeImmatriculation;
        this.factureId = factureId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public StatutIntervention getStatut() { return statut; }
    public void setStatut(StatutIntervention statut) { this.statut = statut; }

    public BigDecimal getCoutMainOeuvre() { return coutMainOeuvre; }
    public void setCoutMainOeuvre(BigDecimal coutMainOeuvre) { this.coutMainOeuvre = coutMainOeuvre; }

    public Long getVehiculeId() { return vehiculeId; }
    public void setVehiculeId(Long vehiculeId) { this.vehiculeId = vehiculeId; }

    public String getVehiculeImmatriculation() { return vehiculeImmatriculation; }
    public void setVehiculeImmatriculation(String vehiculeImmatriculation) { this.vehiculeImmatriculation = vehiculeImmatriculation; }

    public Long getFactureId() { return factureId; }
    public void setFactureId(Long factureId) { this.factureId = factureId; }
}
