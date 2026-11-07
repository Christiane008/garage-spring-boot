package com.garage.garage.controller.api;

import com.garage.garage.dto.ClientDTO;
import com.garage.garage.dto.ClientFormDTO;
import com.garage.garage.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientApiController {

    private final ClientService clientService;

    public ClientApiController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<ClientDTO> lister() {
        return clientService.trouverTousDto();
    }

    @GetMapping("/{id}")
    public ClientDTO trouverParId(@PathVariable Long id) {
        return clientService.trouverParIdDto(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTO creer(@Valid @RequestBody ClientFormDTO clientForm) {
        return clientService.ajouterDto(clientForm);
    }

    @PutMapping("/{id}")
    public ClientDTO modifier(@PathVariable Long id, @Valid @RequestBody ClientFormDTO clientForm) {
        return clientService.modifierDto(id, clientForm);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id) {
        clientService.supprimer(id);
    }
}
