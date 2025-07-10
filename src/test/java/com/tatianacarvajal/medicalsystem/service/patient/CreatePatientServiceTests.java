package com.tatianacarvajal.medicalsystem.service.patient;

import com.tatianacarvajal.medicalsystem.application.service.patient.CreatePatientService;
import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.domain.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreatePatientServiceTests {

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    CreatePatientService createPatientService;

    @Test
    void testCreatePatient() {
        Patient patient = new Patient();
        patient.setName("Luis");
        patient.setDocument("12345");
        patient.setPhone("320630289");

        when(patientRepository.create(any(Patient.class))).thenReturn(patient);

        when(patientRepository.findById(patient.getId()))
                .thenReturn(Optional.of(patient));

        Patient patientResult = createPatientService.create(patient);

        assertNotNull(patientResult);
        assertEquals(patient.getName(), patientResult.getName());
        assertEquals(patient.getDocument(), patientResult.getDocument());
        assertEquals(patient.getPhone(), patientResult.getPhone());

        verify(patientRepository, times(1)).create(patient);

        Optional<Patient> updatedPatient = patientRepository.findById(patient.getId());
        assertTrue(updatedPatient.isPresent());
        assertEquals(updatedPatient.get(), patientResult);
    }

    @Test
    void testCreatePatientNameNullThrowsError() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName(null);
        patient.setDocument("12345");
        patient.setPhone("320630289");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            createPatientService.create(patient);
        });

        assertEquals("Patient name is mandatory", ex.getMessage());
    }

    @Test
    void testCreatePatientNameBlankThrowsError() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName(" ");
        patient.setDocument("12345");
        patient.setPhone("320630289");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            createPatientService.create(patient);
        });

        assertEquals("Patient name is mandatory", ex.getMessage());
    }

    @Test
    void testCreatePatientNameWithNumbersThrowsError() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("987");
        patient.setDocument("12345");
        patient.setPhone("320630289");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            createPatientService.create(patient);
        });

        assertEquals("Patient name can not be only numbers", ex.getMessage());
    }

    @Test
    void testCreatePatientNameWithLessThanThreeCharactersThrowsError() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Lu");
        patient.setDocument("12345");
        patient.setPhone("320630289");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            createPatientService.create(patient);
        });

        assertEquals("Patient name must have at least 3 characters", ex.getMessage());
    }

    @Test
    void testCreatePatientDocumentNullThrowsError() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Luis");
        patient.setDocument(null);
        patient.setPhone("320630289");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            createPatientService.create(patient);
        });

        assertEquals("Patient document number is mandatory", ex.getMessage());
    }

    @Test
    void testCreatePatientDocumentBlankThrowsError() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Luis");
        patient.setDocument(" ");
        patient.setPhone("320630289");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            createPatientService.create(patient);
        });

        assertEquals("Patient document number is mandatory", ex.getMessage());
    }

    @Test
    void testCreatePatientDocumentWithoutNumbersThrowsError() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Luis");
        patient.setDocument("123dc");
        patient.setPhone("320630289");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            createPatientService.create(patient);
        });

        assertEquals("Patient document must be only numbers", ex.getMessage());
    }

    @Test
    void testCreatePatientDocumentWithLessThanFiveDigitsThrowsError() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Luis");
        patient.setDocument("123");
        patient.setPhone("320630289");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            createPatientService.create(patient);
        });

        assertEquals("Patient document must have at least 5 digits", ex.getMessage());
    }
}
