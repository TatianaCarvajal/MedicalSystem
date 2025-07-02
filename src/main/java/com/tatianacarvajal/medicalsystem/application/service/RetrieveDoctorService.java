package com.tatianacarvajal.medicalsystem.application.service;

import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.entities.MedicalSpecialty;
import com.tatianacarvajal.medicalsystem.domain.repository.DoctorRepository;
import com.tatianacarvajal.medicalsystem.domain.usecases.doctor.RetrieveDoctorUseCase;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RetrieveDoctorService implements RetrieveDoctorUseCase {

    private final DoctorRepository doctorRepository;

    public RetrieveDoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        Doctor validatedDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor was not found with that id: " + id));
        return doctorRepository.findById(id);
    }

    @Override
    public List<Doctor> findBySpecialty(MedicalSpecialty specialty) {
        try {
            MedicalSpecialty specialtyName = MedicalSpecialty.valueOf(specialty.name().toUpperCase());
            return doctorRepository.findBySpecialty(specialtyName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid specialty name: " + specialty);
        }
    }
}
