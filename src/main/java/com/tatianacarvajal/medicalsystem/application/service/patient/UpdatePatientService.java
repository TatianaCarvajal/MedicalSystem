package com.tatianacarvajal.medicalsystem.application.service.patient;

import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.domain.repository.PatientRepository;
import com.tatianacarvajal.medicalsystem.domain.usecases.patient.UpdatePatientUseCase;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UpdatePatientService implements UpdatePatientUseCase {

    private final PatientRepository patientRepository;

    public UpdatePatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient update(Patient patient) {
        Long id = patient.getId();
        String patientName = patient.getName();
        String document = patient.getDocument();

        Patient validatedPatient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient was not found with that id: " + id));

        if (patientName == null || patientName.isBlank()) {
            throw new IllegalArgumentException("Patient name is mandatory");
        } else if (document == null || document.isBlank()) {
            throw new IllegalArgumentException("Patient document number is mandatory");
        }

        validatedPatient.setName(patientName);
        validatedPatient.setDocument(document);
        validatedPatient.setPhone(patient.getPhone());
        return patientRepository.update(validatedPatient);
    }
}
