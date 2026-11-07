package com.garage.garage.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GestionnaireErreursWeb {

    @ExceptionHandler(RessourceNonTrouveeException.class)
    public String gererRessourceNonTrouvee(RessourceNonTrouveeException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "erreurs/404";
    }

    @ExceptionHandler(OperationNonAutoriseeException.class)
    public String gererOperationNonAutorisee(OperationNonAutoriseeException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "erreurs/403";
    }

    @ExceptionHandler(StockInsuffisantException.class)
    public String gererStockInsuffisant(StockInsuffisantException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "erreurs/403";
    }

    // Cas "attrape-tout" : ne jamais laisser fuiter une stack trace brute à l'utilisateur
    @ExceptionHandler(Exception.class)
    public String gererErreurInattendue(Exception ex, Model model) {
        model.addAttribute("message", "Une erreur inattendue est survenue.");
        return "erreurs/500";
    }
}
