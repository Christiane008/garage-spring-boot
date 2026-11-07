package com.garage.garage.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Intervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    private StatutIntervention statut;

    // Coût de main d'oeuvre de l'intervention, utilisé par FactureService
    private BigDecimal coutMainOeuvre = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicule vehicule;

    @ManyToMany
    @JoinTable(
        name = "intervention_mecanicien",
        joinColumns = @JoinColumn(name = "intervention_id"),
        inverseJoinColumns = @JoinColumn(name = "mecanicien_id")
    )
    private List<Mecanicien> mecaniciens = new ArrayList<>();

    @OneToMany(mappedBy = "intervention", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneIntervention> lignesIntervention = new ArrayList<>();

    // Côté inverse de Facture.intervention : une intervention génère 0 ou 1 facture
    // (mappedBy car c'est Facture qui porte la clé étrangère intervention_id).
    // Multiplicité 0..1 (une intervention non clôturée n'a pas encore de facture).
    // La règle "clôturée ⇒ facture obligatoire" est garantie par
    // InterventionService.cloturer() (@Transactional), pas par cette annotation :
    // JPA ne peut pas exprimer une contrainte conditionnelle (dépendante du statut)
    // au niveau de la colonne. fetch=LAZY évite qu'Hibernate charge cette association
    // par défaut à chaque lecture d'une intervention.
    @OneToOne(mappedBy = "intervention", fetch = FetchType.LAZY)
    private Facture facture;

    public Intervention() {
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

    public Vehicule getVehicule() { return vehicule; }
    public void setVehicule(Vehicule vehicule) { this.vehicule = vehicule; }

    public List<Mecanicien> getMecaniciens() { return mecaniciens; }
    public void setMecaniciens(List<Mecanicien> mecaniciens) { this.mecaniciens = mecaniciens; }

    public List<LigneIntervention> getLignesIntervention() { return lignesIntervention; }
    public void setLignesIntervention(List<LigneIntervention> lignesIntervention) { this.lignesIntervention = lignesIntervention; }

    public Facture getFacture() { return facture; }
    public void setFacture(Facture facture) { this.facture = facture; }
}
