package com.tatianacarvajal.medicalsystem.application.service;

import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.repository.DoctorRepository;
import com.tatianacarvajal.medicalsystem.domain.usecases.doctor.UpdateDoctorUseCase;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UpdateDoctorService implements UpdateDoctorUseCase {

    private final DoctorRepository doctorRepository;

    public UpdateDoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor update(Doctor doctor) {
        Long id = doctor.getId();
        String doctorName = doctor.getName();

        Doctor validatedDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor was not found with that id: " + id));

        if (doctorName == null || doctorName.isBlank()) {
           throw new IllegalArgumentException("Doctor name is mandatory");
        }

        validatedDoctor.setName(doctorName);
        validatedDoctor.setPhone(doctor.getPhone());
        validatedDoctor.setSpecialty(doctor.getSpecialty());
        return doctorRepository.update(validatedDoctor);
    }
}
