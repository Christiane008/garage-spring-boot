package com.garage.garage.dto;

import java.math.BigDecimal;

public class LigneInterventionDTO {
    private Long id;
    private int quantite;
    private BigDecimal prixUnitaire;
    private Long interventionId;
    private Long pieceId;
    private String pieceNom;

    public LigneInterventionDTO() {
    }

    public LigneInterventionDTO(Long id, int quantite, BigDecimal prixUnitaire, Long interventionId,
                                 Long pieceId, String pieceNom) {
        super();
        this.id = id;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.interventionId = interventionId;
        this.pieceId = pieceId;
        this.pieceNom = pieceNom;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public BigDecimal getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(BigDecimal prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public Long getInterventionId() { return interventionId; }
    public void setInterventionId(Long interventionId) { this.interventionId = interventionId; }

    public Long getPieceId() { return pieceId; }
    public void setPieceId(Long pieceId) { this.pieceId = pieceId; }

    public String getPieceNom() { return pieceNom; }
    public void setPieceNom(String pieceNom) { this.pieceNom = pieceNom; }
}
