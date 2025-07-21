package com.tatianacarvajal.medicalsystem.application.service.patient;

import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.domain.repository.PatientRepository;
import com.tatianacarvajal.medicalsystem.domain.usecases.patient.DeletePatientUseCase;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeletePatientService implements DeletePatientUseCase {

    private final PatientRepository patientRepository;

    public DeletePatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void deleteById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);

        if (patient.isEmpty()) {
            throw new EntityNotFoundException("Patient was not found with that id: " + id);
        } else {
            patientRepository.deleteById(id);
        }
    }
}
