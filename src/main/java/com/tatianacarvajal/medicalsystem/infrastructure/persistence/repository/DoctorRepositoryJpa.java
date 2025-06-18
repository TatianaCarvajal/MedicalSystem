package com.tatianacarvajal.medicalsystem.infrastructure.persistence.repository;

import com.tatianacarvajal.medicalsystem.infrastructure.persistence.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepositoryJpa extends JpaRepository<DoctorEntity, Long> {
}
