package com.garage.garage.repository;

import com.garage.garage.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FactureRepository extends JpaRepository<Facture, Long> {
    Optional<Facture> findByIntervention_Id(Long interventionId);
    List<Facture> findByClientId(Long clientId);
}
