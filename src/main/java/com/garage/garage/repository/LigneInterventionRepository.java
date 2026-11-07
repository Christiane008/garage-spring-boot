package com.garage.garage.repository;

import com.garage.garage.entity.LigneIntervention;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneInterventionRepository extends JpaRepository<LigneIntervention, Long> {
    List<LigneIntervention> findByInterventionId(Long interventionId);
}
