package com.tatianacarvajal.medicalsystem.domain.repository;

import com.tatianacarvajal.medicalsystem.domain.entities.Patient;

import java.util.Optional;

public interface  PatientRepository {
    Patient create(Patient patient);
    Optional<Patient> findById(Long id);
    Patient update(Patient patient);
    void deleteById(Long id);
}
