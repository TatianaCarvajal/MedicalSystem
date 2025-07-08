package com.tatianacarvajal.medicalsystem.service;

import com.tatianacarvajal.medicalsystem.application.service.UpdateDoctorService;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateDoctorServiceTests {

    @Mock
    DoctorRepository doctorRepository;

    @InjectMocks
    UpdateDoctorService updateDoctorService;

    private Doctor doctor;

    @BeforeEach
    void setupDoctor() {
        doctor = new Doctor();
        doctor.setId(2L);
        doctor.setName("Lisa");
        doctor.setSpecialty(MedicalSpecialty.UROLOGY);
        doctor.setPhone("568894627");
    }

    @Test
    void testUpdateDoctor() {
        Doctor newDoctor = new Doctor();
        newDoctor.setId(doctor.getId());
        newDoctor.setName("Juan");
        newDoctor.setSpecialty(MedicalSpecialty.NEUROLOGY);
        newDoctor.setPhone("568894347");

        when(doctorRepository.findById(2L)).thenReturn(Optional.of(doctor));
        when(doctorRepository.update(any(Doctor.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Doctor doctorResult = updateDoctorService.update(newDoctor);

        assertNotNull(doctorResult);
        assertEquals("Juan", doctorResult.getName());
        assertEquals(MedicalSpecialty.NEUROLOGY, doctorResult.getSpecialty());
        assertEquals("568894347", doctorResult.getPhone());
        verify(doctorRepository).update(any(Doctor.class));
    }

    @Test
    void testUpdateDoctorNotFound() {
        doctor.setId(25L);
        when(doctorRepository.findById(25L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(EntityNotFoundException.class, () -> {
            updateDoctorService.update(doctor);
        });

        assertEquals("Doctor was not found with that id: 25", ex.getMessage());
    }

    @Test
    void testCreateDoctorNameNullThrowsError() {
        Doctor newDoctor = new Doctor();
        newDoctor.setId(doctor.getId());
        newDoctor.setName(null);
        newDoctor.setSpecialty(MedicalSpecialty.NEUROLOGY);
        newDoctor.setPhone("568894347");

        when(doctorRepository.findById(2L)).thenReturn(Optional.of(doctor));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            updateDoctorService.update(newDoctor);
        });

        assertEquals("Doctor name is mandatory", ex.getMessage());
    }

    @Test
    void testCreateDoctorNameBlankThrowsError() {
        Doctor newDoctor = new Doctor();
        newDoctor.setId(doctor.getId());
        newDoctor.setName("");
        newDoctor.setSpecialty(MedicalSpecialty.NEUROLOGY);
        newDoctor.setPhone("568894347");

        when(doctorRepository.findById(2L)).thenReturn(Optional.of(doctor));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            updateDoctorService.update(newDoctor);
        });

        assertEquals("Doctor name is mandatory", ex.getMessage());
    }
}
