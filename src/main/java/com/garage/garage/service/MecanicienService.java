package com.garage.garage.service;

import com.garage.garage.entity.Mecanicien;
import com.garage.garage.exception.RessourceNonTrouveeException;
import com.garage.garage.repository.MecanicienRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MecanicienService {

    private final MecanicienRepository mecanicienRepository;

    public MecanicienService(MecanicienRepository mecanicienRepository) {
        this.mecanicienRepository = mecanicienRepository;
    }

    @Transactional(readOnly = true)
    public List<Mecanicien> trouverTous() {
        return mecanicienRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Mecanicien trouverParId(Long id) {
        return mecanicienRepository.findById(id)
                .orElseThrow(() -> new RessourceNonTrouveeException("Mécanicien introuvable avec l'id " + id));
    }

    public Mecanicien ajouter(Mecanicien mecanicien) {
        return mecanicienRepository.save(mecanicien);
    }

    public void supprimer(Long id) {
        Mecanicien mecanicien = trouverParId(id);
        mecanicienRepository.delete(mecanicien);
    }
}
