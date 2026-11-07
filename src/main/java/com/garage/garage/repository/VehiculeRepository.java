package com.garage.garage.repository;

import com.garage.garage.entity.Vehicule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

    List<Vehicule> findByProprietaireId(Long clientId);

    Optional<Vehicule> findByImmatriculation(String immatriculation);

    List<Vehicule> findByMarque(String marque);

    List<Vehicule> findByMarqueContainingIgnoreCase(String marque);

    Page<Vehicule> findByMarque(String marque, Pageable pageable);

    @Query("""
        SELECT v FROM Vehicule v
        WHERE (:marque IS NULL OR v.marque = :marque)
        AND (:immatriculation IS NULL OR v.immatriculation LIKE %:immatriculation%)
        """)
    List<Vehicule> rechercher(@Param("marque") String marque,
                               @Param("immatriculation") String immatriculation);
}
