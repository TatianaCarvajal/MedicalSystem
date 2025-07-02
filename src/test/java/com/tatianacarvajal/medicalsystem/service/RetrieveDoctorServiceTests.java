package com.tatianacarvajal.medicalsystem.service;

import com.tatianacarvajal.medicalsystem.application.service.RetrieveDoctorService;
import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.entities.MedicalSpecialty;
import com.tatianacarvajal.medicalsystem.domain.repository.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RetrieveDoctorServiceTests {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private RetrieveDoctorService retrieveDoctorService;

    private Doctor doctor;

    @BeforeEach
    void setupDoctor() {
        doctor = new Doctor();
        doctor.setId(6L);
        doctor.setName("Jhon");
        doctor.setSpecialty(MedicalSpecialty.CARDIOLOGY);
        doctor.setPhone("568894627");
    }

    @Test
    void testFindDoctorById() {
        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));

        Optional<Doctor> doctorResult = retrieveDoctorService.findById(doctor.getId());

        assertTrue(doctorResult.isPresent());
        assertEquals("Jhon", doctorResult.get().getName());
    }

    @Test
    void testFindDoctorByIdFailure() {
        when(doctorRepository.findById(30L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            retrieveDoctorService.findById(30L);
        });

        assertEquals("Doctor was not found with that id: 30", ex.getMessage());
    }

    @Test
    void testFindByMedicalSpecialty() {
        when(doctorRepository.findBySpecialty(doctor.getSpecialty())).thenReturn(Arrays.asList(doctor));

        List<Doctor> doctorResult = retrieveDoctorService.findBySpecialty(doctor.getSpecialty());

        assertNotNull(doctorResult);
        assertEquals(MedicalSpecialty.CARDIOLOGY, doctorResult.get(0).getSpecialty());
        assertEquals("568894627", doctorResult.get(0).getPhone());
    }

    @Test
    void testFindByMedicalSpecialtyFailure() {
        when(doctorRepository.findBySpecialty(doctor.getSpecialty()))
                .thenThrow(new RuntimeException("Error to find medical specialty"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            retrieveDoctorService.findBySpecialty(doctor.getSpecialty());
        });

        assertEquals("Error to find medical specialty", ex.getMessage());
    }
}
