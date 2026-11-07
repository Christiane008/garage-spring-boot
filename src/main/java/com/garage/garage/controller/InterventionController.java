package com.garage.garage.controller;

import com.garage.garage.dto.InterventionEditFormDTO;
import com.garage.garage.dto.LigneInterventionFormDTO;
import com.garage.garage.entity.Intervention;
import com.garage.garage.entity.StatutIntervention;
import com.garage.garage.service.InterventionService;
import com.garage.garage.service.LigneInterventionService;
import com.garage.garage.service.PieceDetacheeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/interventions")
public class InterventionController {

    private final InterventionService interventionService;
    private final LigneInterventionService ligneInterventionService;
    private final PieceDetacheeService pieceDetacheeService;

    public InterventionController(InterventionService interventionService,
                                   LigneInterventionService ligneInterventionService,
                                   PieceDetacheeService pieceDetacheeService) {
        this.interventionService = interventionService;
        this.ligneInterventionService = ligneInterventionService;
        this.pieceDetacheeService = pieceDetacheeService;
    }

    @GetMapping
    public String liste(@RequestParam(required = false) StatutIntervention statut,
                         @RequestParam(required = false) LocalDate debut,
                         @RequestParam(required = false) LocalDate fin,
                         @RequestParam(defaultValue = "0") int page,
                         Model model) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("dateDebut").descending());
        Page<?> resultats = interventionService.rechercher(statut, debut, fin, pageable);

        model.addAttribute("interventions", resultats.getContent());
        model.addAttribute("pageActuelle", page);
        model.addAttribute("totalPages", resultats.getTotalPages());
        model.addAttribute("statut", statut);
        model.addAttribute("debut", debut);
        model.addAttribute("fin", fin);
        return "interventions/liste";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("intervention", interventionService.trouverParId(id));
        return "interventions/detail";
    }

    @GetMapping("/{id}/modifier")
    public String formulaireModification(@PathVariable Long id, Model model) {
        Intervention intervention = interventionService.trouverParId(id);
        InterventionEditFormDTO form = new InterventionEditFormDTO();
        form.setDescription(intervention.getDescription());
        form.setDateDebut(intervention.getDateDebut());
        form.setCoutMainOeuvre(intervention.getCoutMainOeuvre());
        model.addAttribute("intervention", form);
        model.addAttribute("interventionId", id);
        return "interventions/modifier";
    }

    @PostMapping("/{id}")
    public String modifier(@PathVariable Long id,
                            @Valid @ModelAttribute("intervention") InterventionEditFormDTO form,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("interventionId", id);
            return "interventions/modifier";
        }
        interventionService.modifier(id, form);
        return "redirect:/interventions/" + id;
    }

    // --- Gestion des pièces utilisées (LigneIntervention) ---

    @GetMapping("/{id}/pieces/nouveau")
    public String formulaireAjoutPiece(@PathVariable Long id, Model model) {
        model.addAttribute("interventionId", id);
        model.addAttribute("ligne", new LigneInterventionFormDTO());
        model.addAttribute("pieces", pieceDetacheeService.trouverTous());
        return "interventions/ajouter-piece";
    }

    @PostMapping("/{id}/pieces")
    public String ajouterPiece(@PathVariable Long id,
                                @Valid @ModelAttribute("ligne") LigneInterventionFormDTO form,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("interventionId", id);
            model.addAttribute("pieces", pieceDetacheeService.trouverTous());
            return "interventions/ajouter-piece";
        }
        ligneInterventionService.ajouter(id, form);
        return "redirect:/interventions/" + id;
    }

    @PostMapping("/{id}/pieces/{ligneId}/supprimer")
    public String supprimerPiece(@PathVariable Long id, @PathVariable Long ligneId) {
        ligneInterventionService.supprimer(id, ligneId);
        return "redirect:/interventions/" + id;
    }

    @PostMapping("/{id}/cloturer")
    public String cloturer(@PathVariable Long id) {
        interventionService.cloturer(id);
        return "redirect:/interventions/" + id;
    }

    @PostMapping("/{id}/supprimer")
    public String supprimer(@PathVariable Long id) {
        interventionService.supprimer(id);
        return "redirect:/interventions";
    }
}
