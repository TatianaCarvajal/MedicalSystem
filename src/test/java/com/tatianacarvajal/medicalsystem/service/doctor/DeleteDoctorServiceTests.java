package com.tatianacarvajal.medicalsystem.service.doctor;

import com.tatianacarvajal.medicalsystem.application.service.doctor.DeleteDoctorService;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteDoctorServiceTests {

    @Mock
    DoctorRepository doctorRepository;

    @InjectMocks
    DeleteDoctorService deleteDoctorService;

    private Doctor doctor;

    @BeforeEach
    void setupDoctor() {
        doctor = new Doctor();
        doctor.setId(5L);
        doctor.setName("Maria");
        doctor.setSpecialty(MedicalSpecialty.DERMATOLOGY);
        doctor.setPhone("568394627");
    }

    @Test
    void testDeleteDoctor() {
        when(doctorRepository.create(any(Doctor.class))).thenReturn(doctor);
        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));
        when(doctorRepository.findBySpecialty(MedicalSpecialty.DERMATOLOGY))
                .thenReturn(Collections.singletonList(doctor))
                .thenReturn(Collections.emptyList());

        Doctor doctorResult = doctorRepository.create(doctor);
        assertNotNull(doctorResult);

        List<Doctor> doctorList = doctorRepository.findBySpecialty(MedicalSpecialty.DERMATOLOGY);
        assertFalse(doctorList.isEmpty());

        deleteDoctorService.deleteById(doctor.getId());

        List<Doctor> updatedDoctorList = doctorRepository.findBySpecialty(MedicalSpecialty.DERMATOLOGY);
        assertTrue(updatedDoctorList.isEmpty());

        verify(doctorRepository, times(1)).findById(doctor.getId());
        verify(doctorRepository, times(1)).deleteById(doctor.getId());
    }

    @Test
    void testDeleteDoctorFailure() {
        long doctorId = 10L;
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                deleteDoctorService.deleteById(doctorId)
        );

        assertEquals("Doctor was not found with that id: 10", ex.getMessage());

        verify(doctorRepository, times(1)).findById(doctorId);
        verify(doctorRepository, never()).deleteById(anyLong());
    }
}
