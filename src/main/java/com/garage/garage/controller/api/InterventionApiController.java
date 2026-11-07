package com.garage.garage.controller.api;

import com.garage.garage.dto.InterventionDTO;
import com.garage.garage.dto.InterventionEditFormDTO;
import com.garage.garage.dto.InterventionFormDTO;
import com.garage.garage.dto.LigneInterventionDTO;
import com.garage.garage.dto.LigneInterventionFormDTO;
import com.garage.garage.service.InterventionService;
import com.garage.garage.service.LigneInterventionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interventions")
public class InterventionApiController {

    private final InterventionService interventionService;
    private final LigneInterventionService ligneInterventionService;

    public InterventionApiController(InterventionService interventionService,
                                      LigneInterventionService ligneInterventionService) {
        this.interventionService = interventionService;
        this.ligneInterventionService = ligneInterventionService;
    }

    @GetMapping
    public List<InterventionDTO> lister() {
        return interventionService.trouverTousDto();
    }

    @GetMapping("/{id}")
    public InterventionDTO trouverParId(@PathVariable Long id) {
        return interventionService.trouverParIdDto(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InterventionDTO creer(@Valid @RequestBody InterventionFormDTO interventionForm) {
        return interventionService.ajouterDto(interventionForm);
    }

    @PutMapping("/{id}")
    public InterventionDTO modifier(@PathVariable Long id, @Valid @RequestBody InterventionEditFormDTO interventionForm) {
        return interventionService.modifierDto(id, interventionForm);
    }

    @PutMapping("/{id}/cloturer")
    public InterventionDTO cloturer(@PathVariable Long id) {
        return interventionService.cloturerDto(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id) {
        interventionService.supprimer(id);
    }

    // --- Pièces utilisées (LigneIntervention) ---

    @GetMapping("/{id}/pieces")
    public List<LigneInterventionDTO> listerPieces(@PathVariable Long id) {
        return ligneInterventionService.trouverParInterventionDto(id);
    }

    @PostMapping("/{id}/pieces")
    @ResponseStatus(HttpStatus.CREATED)
    public LigneInterventionDTO ajouterPiece(@PathVariable Long id,
                                              @Valid @RequestBody LigneInterventionFormDTO ligneForm) {
        return ligneInterventionService.ajouterDto(id, ligneForm);
    }

    @DeleteMapping("/{id}/pieces/{ligneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimerPiece(@PathVariable Long id, @PathVariable Long ligneId) {
        ligneInterventionService.supprimer(id, ligneId);
    }
}
