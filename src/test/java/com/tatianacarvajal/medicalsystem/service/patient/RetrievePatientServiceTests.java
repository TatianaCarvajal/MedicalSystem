package com.tatianacarvajal.medicalsystem.service.patient;

import com.tatianacarvajal.medicalsystem.application.service.patient.RetrievePatientService;
import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.domain.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RetrievePatientServiceTests {

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    RetrievePatientService retrievePatientService;

    Patient patient;

    @BeforeEach
    void setupPatient() {
        patient = new Patient();
        patient.setId(1L);
        patient.setName("Jeny");
        patient.setDocument("12345");
        patient.setPhone("320630289");
    }

    @Test
    void testFindPatientById() {
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));

        Optional<Patient> patientResult = retrievePatientService.findById(patient.getId());

        assertTrue(patientResult.isPresent());
        assertEquals("Jeny", patientResult.get().getName());
    }

    @Test
    void testFindPatientByIdFailure() {
        when(patientRepository.findById(2L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            retrievePatientService.findById(2L);
        });

        assertEquals("Patient was not found with that id: 2", ex.getMessage());
    }
}
