package com.tatianacarvajal.medicalsystem.service.patient;

import com.tatianacarvajal.medicalsystem.application.service.patient.DeletePatientService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeletePatientServiceTests {

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    DeletePatientService deletePatientService;

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
    void testDeletePatient() {
        when(patientRepository.findById(patient.getId()))
                .thenReturn(Optional.of(patient))
                .thenReturn(Optional.empty());

        deletePatientService.deleteById(patient.getId());

        Optional<Patient> patientResult = patientRepository.findById(patient.getId());
        assertTrue(patientResult.isEmpty());

        verify(patientRepository, times(2)).findById(patient.getId());
        verify(patientRepository, times(1)).deleteById(patient.getId());
    }

    @Test
    void testDeletePatientFailure() {
        long patientId = 2L;
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            deletePatientService.deleteById(patientId);
        });

        assertEquals("Patient was not found with that id: 2", ex.getMessage());

        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, never()).deleteById(anyLong());
    }
}
