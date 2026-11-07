package com.garage.garage.dto;

import java.math.BigDecimal;

public class PieceDetacheeDTO {
    private Long id;
    private String reference;
    private String nom;
    private BigDecimal prixUnitaire;
    private int quantiteStock;

    public PieceDetacheeDTO() {
    }

    public PieceDetacheeDTO(Long id, String reference, String nom, BigDecimal prixUnitaire, int quantiteStock) {
        super();
        this.id = id;
        this.reference = reference;
        this.nom = nom;
        this.prixUnitaire = prixUnitaire;
        this.quantiteStock = quantiteStock;
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
}
