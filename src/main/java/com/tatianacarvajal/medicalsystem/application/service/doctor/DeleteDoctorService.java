package com.tatianacarvajal.medicalsystem.application.service.doctor;

import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.repository.DoctorRepository;
import com.tatianacarvajal.medicalsystem.domain.usecases.doctor.DeleteDoctorUseCase;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteDoctorService implements DeleteDoctorUseCase {

    private final DoctorRepository doctorRepository;

    public DeleteDoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public void deleteById(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);

        if (doctor.isEmpty()) {
            throw new EntityNotFoundException("Doctor was not found with that id: " + id);
        } else {
            doctorRepository.deleteById(id);
        }
    }
}
