package com.tatianacarvajal.medicalsystem.domain.usecases.patient;

import com.tatianacarvajal.medicalsystem.domain.entities.Patient;

public interface UpdatePatientUseCase {
    Patient update(Patient patient);
}
