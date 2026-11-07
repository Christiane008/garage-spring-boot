package com.garage.garage.repository;

import com.garage.garage.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByNom(String nom);
    List<Client> findByEmail(String email);
    List<Client> findByNomAndPrenom(String nom, String prenom);
    List<Client> findByNomContainingIgnoreCase(String nom);
}
