package com.tatianacarvajal.medicalsystem.domain.usecases.patientusecase;

import com.tatianacarvajal.medicalsystem.domain.entities.Patient;

public interface CreatePatientUseCase {
    Patient create(Patient patient);
}
