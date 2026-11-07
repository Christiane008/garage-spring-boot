package com.garage.garage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccueilController {

    @GetMapping("/")
    public String accueil() {
        return "accueil";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Cible de SecurityConfig.accessDeniedPage() : atteinte quand un utilisateur
    // authentifié n'a pas le rôle requis pour l'URL demandée (403 Spring Security,
    // distinct des OperationNonAutoriseeException levées dans nos services).
    @GetMapping("/acces-refuse")
    public String accesRefuse(Model model) {
        model.addAttribute("message", "Vous n'avez pas les droits nécessaires pour accéder à cette page.");
        return "erreurs/403";
    }
}
