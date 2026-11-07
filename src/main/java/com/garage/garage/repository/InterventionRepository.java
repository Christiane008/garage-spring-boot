package com.garage.garage.repository;

import com.garage.garage.entity.Intervention;
import com.garage.garage.entity.StatutIntervention;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InterventionRepository extends JpaRepository<Intervention, Long> {

    List<Intervention> findByVehiculeId(Long vehiculeId);

    List<Intervention> findByDateDebut(LocalDate date);

    List<Intervention> findByDateDebutBetween(LocalDate debut, LocalDate fin);

    List<Intervention> findByStatut(StatutIntervention statut);

    List<Intervention> findByStatutOrderByDateDebutDesc(StatutIntervention statut);

    @Query("SELECT i FROM Intervention i WHERE i.vehicule.proprietaire.id = :clientId")
    List<Intervention> trouverParClient(@Param("clientId") Long clientId);

    @Query("""
        SELECT i FROM Intervention i
        WHERE (:statut IS NULL OR i.statut = :statut)
        AND (:debut IS NULL OR i.dateDebut >= :debut)
        AND (:fin IS NULL OR i.dateDebut <= :fin)
        """)
    Page<Intervention> rechercher(@Param("statut") StatutIntervention statut,
                                   @Param("debut") LocalDate debut,
                                   @Param("fin") LocalDate fin,
                                   Pageable pageable);
}
