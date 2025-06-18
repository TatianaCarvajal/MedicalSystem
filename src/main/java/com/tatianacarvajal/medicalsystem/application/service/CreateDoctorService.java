package com.tatianacarvajal.medicalsystem.application.service;

import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.repository.DoctorRepository;
import com.tatianacarvajal.medicalsystem.domain.usecases.doctor.CreateDoctorUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreateDoctorService implements CreateDoctorUseCase {

    private final DoctorRepository doctorRepository;

    public CreateDoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor create(Doctor doctor) {
        if (doctor.getName() == null || doctor.getName().isBlank()) {
            throw new IllegalArgumentException("Doctor name is mandatory");
        }
        return doctorRepository.create(doctor);
    }
}

