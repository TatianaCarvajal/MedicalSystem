package com.tatianacarvajal.medicalsystem.infrastructure.persistence.repository.patient;

import com.tatianacarvajal.medicalsystem.infrastructure.persistence.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepositoryJpa extends JpaRepository<PatientEntity, Long> {
}
