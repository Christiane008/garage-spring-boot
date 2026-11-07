package com.garage.garage.mapper;

import com.garage.garage.dto.PieceDetacheeDTO;
import com.garage.garage.dto.PieceDetacheeFormDTO;
import com.garage.garage.entity.PieceDetachee;
import org.springframework.stereotype.Component;

@Component
public class PieceDetacheeMapper {

    public PieceDetacheeDTO toDto(PieceDetachee piece) {
        return new PieceDetacheeDTO(
                piece.getId(),
                piece.getReference(),
                piece.getNom(),
                piece.getPrixUnitaire(),
                piece.getQuantiteStock()
        );
    }

    public PieceDetachee toEntity(PieceDetacheeFormDTO form) {
        PieceDetachee piece = new PieceDetachee();
        updateEntity(piece, form);
        return piece;
    }

    public void updateEntity(PieceDetachee piece, PieceDetacheeFormDTO form) {
        piece.setReference(form.getReference());
        piece.setNom(form.getNom());
        piece.setPrixUnitaire(form.getPrixUnitaire());
        piece.setQuantiteStock(form.getQuantiteStock());
    }
}
