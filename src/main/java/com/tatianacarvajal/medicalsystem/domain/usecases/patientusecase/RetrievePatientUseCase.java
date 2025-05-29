package com.tatianacarvajal.medicalsystem.domain.usecases.patientusecase;

import com.tatianacarvajal.medicalsystem.domain.entities.Patient;

import java.util.Optional;

public interface RetrievePatientUseCase {
    Optional<Patient> findById(Long id);
}
