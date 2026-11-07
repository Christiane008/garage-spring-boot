package com.garage.garage.controller;

import com.garage.garage.entity.Vehicule;
import com.garage.garage.service.ClientService;
import com.garage.garage.service.VehiculeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vehicules")
public class VehiculeController {

    private final VehiculeService vehiculeService;
    private final ClientService clientService;

    public VehiculeController(VehiculeService vehiculeService, ClientService clientService) {
        this.vehiculeService = vehiculeService;
        this.clientService = clientService;
    }

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("vehicules", vehiculeService.trouverTous());
        return "vehicules/liste";
    }

    @GetMapping("/recherche")
    public String recherche(@RequestParam(required = false) String marque,
                             @RequestParam(required = false) String immatriculation,
                             Model model) {
        model.addAttribute("vehicules", vehiculeService.rechercher(marque, immatriculation));
        model.addAttribute("marque", marque);
        model.addAttribute("immatriculation", immatriculation);
        return "vehicules/recherche";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("vehicule", vehiculeService.trouverParId(id));
        return "vehicules/detail";
    }

    @PostMapping("/{id}/supprimer")
    public String supprimer(@PathVariable Long id) {
        vehiculeService.supprimer(id);
        return "redirect:/vehicules";
    }
}
