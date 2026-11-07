package com.garage.garage.controller.api;

import com.garage.garage.dto.VehiculeDTO;
import com.garage.garage.dto.VehiculeFormDTO;
import com.garage.garage.service.VehiculeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
public class VehiculeApiController {

    private final VehiculeService vehiculeService;

    public VehiculeApiController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    @GetMapping
    public List<VehiculeDTO> lister() {
        return vehiculeService.trouverTousDto();
    }

    @GetMapping("/{id}")
    public VehiculeDTO trouverParId(@PathVariable Long id) {
        return vehiculeService.trouverParIdDto(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeDTO creer(@Valid @RequestBody VehiculeFormDTO vehiculeForm) {
        return vehiculeService.ajouterDto(vehiculeForm);
    }

    @PutMapping("/{id}")
    public VehiculeDTO modifier(@PathVariable Long id, @Valid @RequestBody VehiculeFormDTO vehiculeForm) {
        return vehiculeService.modifierDto(id, vehiculeForm);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id) {
        vehiculeService.supprimer(id);
    }
}
