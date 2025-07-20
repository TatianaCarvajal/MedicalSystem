package com.tatianacarvajal.medicalsystem.application.service.patient;

import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.domain.repository.PatientRepository;
import com.tatianacarvajal.medicalsystem.domain.usecases.patient.RetrievePatientUseCase;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RetrievePatientService implements RetrievePatientUseCase {

    private final PatientRepository patientRepository;

    public RetrievePatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Optional<Patient> findById(Long id) {
        Patient validatedPatient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient was not found with that id: " + id));
        return patientRepository.findById(id);
    }
}
