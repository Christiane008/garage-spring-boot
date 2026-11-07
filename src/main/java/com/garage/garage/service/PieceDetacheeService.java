package com.garage.garage.service;

import com.garage.garage.dto.PieceDetacheeDTO;
import com.garage.garage.dto.PieceDetacheeFormDTO;
import com.garage.garage.entity.PieceDetachee;
import com.garage.garage.exception.RessourceNonTrouveeException;
import com.garage.garage.mapper.PieceDetacheeMapper;
import com.garage.garage.repository.PieceDetacheeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PieceDetacheeService {

    private final PieceDetacheeRepository pieceDetacheeRepository;
    private final PieceDetacheeMapper pieceDetacheeMapper;

    public PieceDetacheeService(PieceDetacheeRepository pieceDetacheeRepository,
                                 PieceDetacheeMapper pieceDetacheeMapper) {
        this.pieceDetacheeRepository = pieceDetacheeRepository;
        this.pieceDetacheeMapper = pieceDetacheeMapper;
    }

    @Transactional(readOnly = true)
    public List<PieceDetachee> trouverTous() {
        return pieceDetacheeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PieceDetachee trouverParId(Long id) {
        return pieceDetacheeRepository.findById(id)
                .orElseThrow(() -> new RessourceNonTrouveeException("Pièce introuvable avec l'id " + id));
    }

    public PieceDetachee ajouter(PieceDetacheeFormDTO form) {
        PieceDetachee piece = pieceDetacheeMapper.toEntity(form);
        return pieceDetacheeRepository.save(piece);
    }

    public PieceDetachee modifier(Long id, PieceDetacheeFormDTO form) {
        PieceDetachee piece = trouverParId(id);
        pieceDetacheeMapper.updateEntity(piece, form);
        return pieceDetacheeRepository.save(piece);
    }

    public void supprimer(Long id) {
        PieceDetachee piece = trouverParId(id);
        pieceDetacheeRepository.delete(piece);
    }

    // --- DTOs ---

    @Transactional(readOnly = true)
    public List<PieceDetacheeDTO> trouverTousDto() {
        return trouverTous().stream().map(pieceDetacheeMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PieceDetacheeDTO trouverParIdDto(Long id) {
        return pieceDetacheeMapper.toDto(trouverParId(id));
    }

    public PieceDetacheeDTO ajouterDto(PieceDetacheeFormDTO form) {
        return pieceDetacheeMapper.toDto(ajouter(form));
    }

    public PieceDetacheeDTO modifierDto(Long id, PieceDetacheeFormDTO form) {
        return pieceDetacheeMapper.toDto(modifier(id, form));
    }
}
