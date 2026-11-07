package com.garage.garage.config;

import com.garage.garage.entity.Role;
import com.garage.garage.repository.UtilisateurRepository;
import com.garage.garage.service.UtilisateurService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @org.springframework.context.annotation.Bean
    public CommandLineRunner initUtilisateurs(UtilisateurRepository utilisateurRepository,
                                               UtilisateurService utilisateurService) {
        return args -> {
            if (utilisateurRepository.count() == 0) {
                utilisateurService.creer("admin", "admin123", Role.ADMIN);
                utilisateurService.creer("reception", "reception123", Role.RECEPTION);
                utilisateurService.creer("mecano", "mecano123", Role.MECANICIEN);
            }
        };
    }
}
