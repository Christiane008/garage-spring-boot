package com.garage.garage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

public class InterventionFormDTO {

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "Le véhicule est obligatoire")
    private Long vehiculeId;

    @PositiveOrZero(message = "Le coût de main d'oeuvre ne peut pas être négatif")
    private BigDecimal coutMainOeuvre = BigDecimal.ZERO;

    public InterventionFormDTO() {
    }

    public InterventionFormDTO(String description, LocalDate dateDebut, Long vehiculeId, BigDecimal coutMainOeuvre) {
        super();
        this.description = description;
        this.dateDebut = dateDebut;
        this.vehiculeId = vehiculeId;
        this.coutMainOeuvre = coutMainOeuvre;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public Long getVehiculeId() { return vehiculeId; }
    public void setVehiculeId(Long vehiculeId) { this.vehiculeId = vehiculeId; }

    public BigDecimal getCoutMainOeuvre() { return coutMainOeuvre; }
    public void setCoutMainOeuvre(BigDecimal coutMainOeuvre) { this.coutMainOeuvre = coutMainOeuvre; }
}
