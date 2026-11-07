package com.garage.garage.controller;

import com.garage.garage.dto.PieceDetacheeFormDTO;
import com.garage.garage.entity.PieceDetachee;
import com.garage.garage.service.PieceDetacheeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pieces")
public class PieceDetacheeController {

    private final PieceDetacheeService pieceDetacheeService;

    public PieceDetacheeController(PieceDetacheeService pieceDetacheeService) {
        this.pieceDetacheeService = pieceDetacheeService;
    }

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("pieces", pieceDetacheeService.trouverTous());
        return "pieces/liste";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("piece", pieceDetacheeService.trouverParId(id));
        return "pieces/detail";
    }

    @GetMapping("/nouveau")
    public String formulaireCreation(Model model) {
        model.addAttribute("piece", new PieceDetacheeFormDTO());
        model.addAttribute("estModification", false);
        return "pieces/formulaire";
    }

    @PostMapping
    public String ajouter(@Valid @ModelAttribute("piece") PieceDetacheeFormDTO pieceForm,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("estModification", false);
            return "pieces/formulaire";
        }
        pieceDetacheeService.ajouter(pieceForm);
        return "redirect:/pieces";
    }

    @GetMapping("/{id}/modifier")
    public String formulaireModification(@PathVariable Long id, Model model) {
        PieceDetachee piece = pieceDetacheeService.trouverParId(id);
        PieceDetacheeFormDTO form = new PieceDetacheeFormDTO();
        form.setReference(piece.getReference());
        form.setNom(piece.getNom());
        form.setPrixUnitaire(piece.getPrixUnitaire());
        form.setQuantiteStock(piece.getQuantiteStock());
        model.addAttribute("piece", form);
        model.addAttribute("pieceId", id);
        model.addAttribute("estModification", true);
        return "pieces/formulaire";
    }

    @PostMapping("/{id}")
    public String modifier(@PathVariable Long id,
                            @Valid @ModelAttribute("piece") PieceDetacheeFormDTO pieceForm,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pieceId", id);
            model.addAttribute("estModification", true);
            return "pieces/formulaire";
        }
        pieceDetacheeService.modifier(id, pieceForm);
        return "redirect:/pieces";
    }

    @PostMapping("/{id}/supprimer")
    public String supprimer(@PathVariable Long id) {
        pieceDetacheeService.supprimer(id);
        return "redirect:/pieces";
    }
}
