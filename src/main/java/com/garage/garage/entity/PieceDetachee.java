package com.garage.garage.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PieceDetachee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String reference;

    private String nom;
    private BigDecimal prixUnitaire;
    private int quantiteStock;

    // Côté inverse : permet de savoir dans quelles interventions (via les lignes)
    // cette pièce a été utilisée, sans faire de requête séparée.
    @OneToMany(mappedBy = "piece")
    private List<LigneIntervention> lignesIntervention = new ArrayList<>();

    public PieceDetachee() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public BigDecimal getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(BigDecimal prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public int getQuantiteStock() { return quantiteStock; }
    public void setQuantiteStock(int quantiteStock) { this.quantiteStock = quantiteStock; }

    public List<LigneIntervention> getLignesIntervention() { return lignesIntervention; }
    public void setLignesIntervention(List<LigneIntervention> lignesIntervention) { this.lignesIntervention = lignesIntervention; }
}
