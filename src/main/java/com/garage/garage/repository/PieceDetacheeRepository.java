package com.garage.garage.repository;

import com.garage.garage.entity.PieceDetachee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PieceDetacheeRepository extends JpaRepository<PieceDetachee, Long> {
    Optional<PieceDetachee> findByReference(String reference);
}
