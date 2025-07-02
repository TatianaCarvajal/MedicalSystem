package com.tatianacarvajal.medicalsystem.domain.usecases.doctor;

import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.entities.MedicalSpecialty;

import java.util.List;
import java.util.Optional;

public interface RetrieveDoctorUseCase {
    Optional<Doctor> findById(Long id);
    List<Doctor> findBySpecialty(MedicalSpecialty specialty);
}
