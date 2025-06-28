package com.tatianacarvajal.medicalsystem.domain.repository;

import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.entities.MedicalSpecialty;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository {
    Doctor create(Doctor doctor);
    Optional<Doctor> findById(Long id);
    List<Doctor> findBySpecialty(MedicalSpecialty medicalSpecialty);
    Doctor update(Doctor doctor);
    void deleteById(Long id);
}
