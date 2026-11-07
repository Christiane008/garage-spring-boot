package com.garage.garage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GestionnaireErreursApi {

    @ExceptionHandler(RessourceNonTrouveeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> gererRessourceNonTrouvee(RessourceNonTrouveeException ex) {
        return Map.of("erreur", ex.getMessage());
    }

    @ExceptionHandler(OperationNonAutoriseeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> gererOperationNonAutorisee(OperationNonAutoriseeException ex) {
        return Map.of("erreur", ex.getMessage());
    }

    @ExceptionHandler(KilometrageInvalideException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> gererKilometrageInvalide(KilometrageInvalideException ex) {
        return Map.of("erreur", ex.getMessage());
    }

    @ExceptionHandler(StockInsuffisantException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> gererStockInsuffisant(StockInsuffisantException ex) {
        return Map.of("erreur", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> gererErreurInattendue(Exception ex) {
        return Map.of("erreur", "Une erreur inattendue est survenue.");
    }
}
