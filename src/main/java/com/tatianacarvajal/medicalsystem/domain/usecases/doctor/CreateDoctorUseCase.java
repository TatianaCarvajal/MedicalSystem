package com.tatianacarvajal.medicalsystem.domain.usecases.doctor;

import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;

public interface CreateDoctorUseCase {
    Doctor create(Doctor doctor);
}
