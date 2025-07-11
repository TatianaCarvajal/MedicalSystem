package com.tatianacarvajal.medicalsystem.service.patient;

import com.tatianacarvajal.medicalsystem.application.service.patient.UpdatePatientService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdatePatientServiceTests {

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    UpdatePatientService updatePatientService;

    private Patient patient;

    @BeforeEach
    void setupPatient() {
        patient = new Patient();
        patient.setId(2L);
        patient.setName("Carlos");
        patient.setDocument("12345");
        patient.setPhone("320630289");
    }

    @Test
    void testUpdatePatient() {
        Patient newPatient = new Patient();
        newPatient.setId(patient.getId());
        newPatient.setName("Carl");
        newPatient.setDocument("12345");
        newPatient.setPhone("320630289");

        when(patientRepository.findById(2L)).thenReturn(Optional.of(patient));

        when(patientRepository.update(any(Patient.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Patient patientResult = updatePatientService.update(newPatient);

        assertNotNull(patientResult);
        assertEquals(newPatient.getName(), patientResult.getName());
        assertEquals(newPatient.getDocument(), patientResult.getDocument());
        assertEquals(newPatient.getPhone(), patientResult.getPhone());

        verify(patientRepository, times(1)).update(patient);
    }

    @Test
    void testUpdatePatientNotFound() {
        patient.setId(15L);
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.empty());

        Exception ex = assertThrows(EntityNotFoundException.class, () -> {
            updatePatientService.update(patient);
        });

        assertEquals("Patient was not found with that id: 15", ex.getMessage());
    }

    @Test
    void testUpdatePatientNameNullThrowsError() {
        Patient newPatient = new Patient();
        newPatient.setId(patient.getId());
        newPatient.setName(null);
        newPatient.setDocument("12345");
        newPatient.setPhone("320630289");

        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            updatePatientService.update(newPatient);
        });

        assertEquals("Patient name is mandatory", ex.getMessage());
    }

    @Test
    void testUpdatePatientNameBlankThrowsError() {
        Patient newPatient = new Patient();
        newPatient.setId(patient.getId());
        newPatient.setName(" ");
        newPatient.setDocument("12345");
        newPatient.setPhone("320630289");

        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            updatePatientService.update(newPatient);
        });

        assertEquals("Patient name is mandatory", ex.getMessage());
    }

    @Test
    void testUpdatePatientDocumentNullThrowsError() {
        Patient newPatient = new Patient();
        newPatient.setId(patient.getId());
        newPatient.setName("Carl");
        newPatient.setDocument(null);
        newPatient.setPhone("320630289");

        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            updatePatientService.update(newPatient);
        });

        assertEquals("Patient document number is mandatory", ex.getMessage());
    }

    @Test
    void testUpdatePatientDocumentBlankThrowsError() {
        Patient newPatient = new Patient();
        newPatient.setId(patient.getId());
        newPatient.setName("Carl");
        newPatient.setDocument(" ");
        newPatient.setPhone("320630289");

        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            updatePatientService.update(newPatient);
        });

        assertEquals("Patient document number is mandatory", ex.getMessage());
    }
}
