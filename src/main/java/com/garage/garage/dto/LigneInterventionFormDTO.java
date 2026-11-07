package com.garage.garage.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class LigneInterventionFormDTO {

    @NotNull(message = "La pièce est obligatoire")
    private Long pieceId;

    @Positive(message = "La quantité doit être supérieure à 0")
    private int quantite;

    public LigneInterventionFormDTO() {
    }

    public LigneInterventionFormDTO(Long pieceId, int quantite) {
        super();
        this.pieceId = pieceId;
        this.quantite = quantite;
    }

    public Long getPieceId() { return pieceId; }
    public void setPieceId(Long pieceId) { this.pieceId = pieceId; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
}
