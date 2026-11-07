package com.garage.garage.controller.api;

import com.garage.garage.dto.PieceDetacheeDTO;
import com.garage.garage.dto.PieceDetacheeFormDTO;
import com.garage.garage.service.PieceDetacheeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pieces")
public class PieceDetacheeApiController {

    private final PieceDetacheeService pieceDetacheeService;

    public PieceDetacheeApiController(PieceDetacheeService pieceDetacheeService) {
        this.pieceDetacheeService = pieceDetacheeService;
    }

    @GetMapping
    public List<PieceDetacheeDTO> lister() {
        return pieceDetacheeService.trouverTousDto();
    }

    @GetMapping("/{id}")
    public PieceDetacheeDTO trouverParId(@PathVariable Long id) {
        return pieceDetacheeService.trouverParIdDto(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PieceDetacheeDTO creer(@Valid @RequestBody PieceDetacheeFormDTO pieceForm) {
        return pieceDetacheeService.ajouterDto(pieceForm);
    }

    @PutMapping("/{id}")
    public PieceDetacheeDTO modifier(@PathVariable Long id, @Valid @RequestBody PieceDetacheeFormDTO pieceForm) {
        return pieceDetacheeService.modifierDto(id, pieceForm);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id) {
        pieceDetacheeService.supprimer(id);
    }
}
