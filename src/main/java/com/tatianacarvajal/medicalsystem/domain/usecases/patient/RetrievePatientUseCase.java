package com.tatianacarvajal.medicalsystem.domain.usecases.patient;

import com.tatianacarvajal.medicalsystem.domain.entities.Patient;

import java.util.Optional;

public interface RetrievePatientUseCase {
    Optional<Patient> findById(Long id);
}
