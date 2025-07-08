package com.tatianacarvajal.medicalsystem.application.service;

import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.repository.DoctorRepository;
import com.tatianacarvajal.medicalsystem.domain.usecases.doctor.CreateDoctorUseCase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateDoctorService implements CreateDoctorUseCase {

    private final DoctorRepository doctorRepository;

    public CreateDoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor create(Doctor doctor) {
        String doctorName = doctor.getName();
        List<String> errors = new ArrayList<>();

        if (doctorName == null || doctorName.isBlank()) {
            errors.add("Doctor name is mandatory");
        } else {
            if (doctorName.matches("\\d+")) {
                errors.add("Doctor name can not be only numbers");
            }
            if (doctorName.length() < 3) {
                errors.add("Doctor name must have at least 3 characters");
            }
        }
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errors));
        }
        return doctorRepository.create(doctor);
    }
}

