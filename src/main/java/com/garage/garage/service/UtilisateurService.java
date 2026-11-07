package com.garage.garage.service;

import com.garage.garage.entity.Role;
import com.garage.garage.entity.Utilisateur;
import com.garage.garage.repository.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurService(UtilisateurRepository utilisateurRepository,
                               PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Utilisateur creer(String username, String motDePasseEnClair, Role role) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setUsername(username);
        utilisateur.setMotDePasse(passwordEncoder.encode(motDePasseEnClair)); // jamais en clair !
        utilisateur.setRole(role);
        return utilisateurRepository.save(utilisateur);
    }
}
