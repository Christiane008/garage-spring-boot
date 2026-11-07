package com.garage.garage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

// DTO utilisé pour modifier une intervention EXISTANTE (description, date de début,
// coût de main d'oeuvre). Le véhicule n'est volontairement pas modifiable ici :
// changer le véhicule d'une intervention déjà créée n'a pas de sens métier.
public class InterventionEditFormDTO {

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "Le coût de main d'oeuvre est obligatoire")
    @PositiveOrZero(message = "Le coût de main d'oeuvre ne peut pas être négatif")
    private BigDecimal coutMainOeuvre;

    public InterventionEditFormDTO() {
    }

    public InterventionEditFormDTO(String description, LocalDate dateDebut, BigDecimal coutMainOeuvre) {
        super();
        this.description = description;
        this.dateDebut = dateDebut;
        this.coutMainOeuvre = coutMainOeuvre;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public BigDecimal getCoutMainOeuvre() { return coutMainOeuvre; }
    public void setCoutMainOeuvre(BigDecimal coutMainOeuvre) { this.coutMainOeuvre = coutMainOeuvre; }
}
