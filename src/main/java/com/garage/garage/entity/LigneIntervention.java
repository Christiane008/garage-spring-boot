package com.garage.garage.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class LigneIntervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantite;

    // prix au moment de l'intervention, indépendant d'une évolution future du tarif
    private BigDecimal prixUnitaire;

    @ManyToOne
    @JoinColumn(name = "intervention_id", nullable = false)
    private Intervention intervention;

    @ManyToOne
    @JoinColumn(name = "piece_id", nullable = false)
    private PieceDetachee piece;

    public LigneIntervention() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public BigDecimal getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(BigDecimal prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public Intervention getIntervention() { return intervention; }
    public void setIntervention(Intervention intervention) { this.intervention = intervention; }

    public PieceDetachee getPiece() { return piece; }
    public void setPiece(PieceDetachee piece) { this.piece = piece; }
}
