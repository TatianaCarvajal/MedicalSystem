package com.tatianacarvajal.medicalsystem.application.service.patient;

import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.domain.repository.PatientRepository;
import com.tatianacarvajal.medicalsystem.domain.usecases.patient.CreatePatientUseCase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreatePatientService implements CreatePatientUseCase {

    private final PatientRepository patientRepository;

    public CreatePatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient create(Patient patient) {
        validatePatient(patient);
        return patientRepository.create(patient);
    }

    private void validatePatient(Patient patient) {
        List<String> errors = new ArrayList<>();

        validateName(patient.getName(), errors);
        validateDocument(patient.getDocument(), errors);

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errors));
        }
    }

    private void validateName(String name, List<String> errors) {
        if (name == null || name.isBlank()) {
            errors.add("Patient name is mandatory");
        } else {
            if (name.matches("\\d+")) {
                errors.add("Patient name can not be only numbers");
            }
            if (name.length() < 3) {
                errors.add("Patient name must have at least 3 characters");
            }
        }
    }

    private void validateDocument(String document, List<String> errors) {
        if (document == null || document.isBlank()) {
            errors.add("Patient document number is mandatory");
        } else {
            if (!document.matches("\\d+")) {
                errors.add("Patient document must be only numbers");
            }
            if (document.length() < 5) {
                errors.add("Patient document must have at least 5 digits");
            }
        }
    }
}
