package com.tatianacarvajal.medicalsystem.domain.usecases.doctorusecase;

import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;

public interface CreateDoctorUseCase {
    Doctor create(Doctor doctor);
}
