package com.tatianacarvajal.medicalsystem.infrastructure.persistence.repository;

import com.tatianacarvajal.medicalsystem.domain.entities.MedicalSpecialty;
import com.tatianacarvajal.medicalsystem.infrastructure.persistence.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepositoryJpa extends JpaRepository<DoctorEntity, Long> {
    List<DoctorEntity> findByMedicalSpecialty(MedicalSpecialty medicalSpecialty);
}
