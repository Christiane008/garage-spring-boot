package com.garage.garage.repository;

import com.garage.garage.entity.Mecanicien;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MecanicienRepository extends JpaRepository<Mecanicien, Long> {
}
