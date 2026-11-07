package com.garage.garage.controller.api;

import com.garage.garage.dto.FactureDTO;
import com.garage.garage.service.FactureService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/factures")
public class FactureApiController {

    private final FactureService factureService;

    public FactureApiController(FactureService factureService) {
        this.factureService = factureService;
    }

    @GetMapping
    public List<FactureDTO> lister() {
        return factureService.trouverTousDto();
    }

    @GetMapping("/{id}")
    public FactureDTO trouverParId(@PathVariable Long id) {
        return factureService.trouverParIdDto(id);
    }
}
