package com.garage.garage.controller;

import com.garage.garage.dto.ClientFormDTO;
import com.garage.garage.entity.Client;
import com.garage.garage.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String liste(@RequestParam(required = false) String nom, Model model) {
        model.addAttribute("clients", clientService.rechercherParNom(nom));
        model.addAttribute("nom", nom);
        return "clients/liste";
    }

    @GetMapping("/nouveau")
    public String formulaireCreation(Model model) {
        model.addAttribute("client", new ClientFormDTO());
        model.addAttribute("estModification", false);
        return "clients/formulaire";
    }

    @PostMapping
    public String ajouter(@Valid @ModelAttribute("client") ClientFormDTO clientForm,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("estModification", false);
            return "clients/formulaire";
        }
        clientService.ajouter(clientForm);
        return "redirect:/clients";
    }

    @GetMapping("/{id}/modifier")
    public String formulaireModification(@PathVariable Long id, Model model) {
        Client client = clientService.trouverParId(id);
        ClientFormDTO form = new ClientFormDTO();
        form.setNom(client.getNom());
        form.setPrenom(client.getPrenom());
        form.setEmail(client.getEmail());
        form.setTelephone(client.getTelephone());
        model.addAttribute("client", form);
        model.addAttribute("clientId", id);
        model.addAttribute("estModification", true);
        return "clients/formulaire";
    }

    @PostMapping("/{id}")
    public String modifier(@PathVariable Long id,
                            @Valid @ModelAttribute("client") ClientFormDTO clientForm,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clientId", id);
            model.addAttribute("estModification", true);
            return "clients/formulaire";
        }
        clientService.modifier(id, clientForm);
        return "redirect:/clients";
    }

    @PostMapping("/{id}/supprimer")
    public String supprimer(@PathVariable Long id) {
        clientService.supprimer(id);
        return "redirect:/clients";
    }
}
