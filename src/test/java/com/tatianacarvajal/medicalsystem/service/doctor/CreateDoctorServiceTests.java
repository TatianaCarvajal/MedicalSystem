package com.tatianacarvajal.medicalsystem.service.doctor;

import com.tatianacarvajal.medicalsystem.application.service.doctor.CreateDoctorService;
import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.entities.MedicalSpecialty;
import com.tatianacarvajal.medicalsystem.domain.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateDoctorServiceTests {

    @Mock
    DoctorRepository doctorRepository;

    @InjectMocks
    CreateDoctorService createDoctorService;

    @Test
    void testCreateDoctor() {
        when(doctorRepository.findBySpecialty(MedicalSpecialty.ENDOCRINOLOGY)).thenReturn(new ArrayList<>());

        Doctor createdDoctor = new Doctor();
        createdDoctor.setName("Sara");
        createdDoctor.setSpecialty(MedicalSpecialty.ENDOCRINOLOGY);
        createdDoctor.setPhone("568894347");

        when(doctorRepository.create(any(Doctor.class))).thenReturn(createdDoctor);

        when(doctorRepository.findBySpecialty(MedicalSpecialty.ENDOCRINOLOGY))
                .thenReturn(Collections.singletonList(createdDoctor));

        Doctor doctorResult = createDoctorService.create(createdDoctor);

        assertNotNull(doctorResult);
        assertEquals(createdDoctor.getName(), doctorResult.getName());
        assertEquals(createdDoctor.getSpecialty(), doctorResult.getSpecialty());
        assertEquals(createdDoctor.getPhone(), doctorResult.getPhone());

        verify(doctorRepository, times(1)).create(createdDoctor);

        List<Doctor> updatedDoctorList = doctorRepository.findBySpecialty(MedicalSpecialty.ENDOCRINOLOGY);
        assertFalse(updatedDoctorList.isEmpty());
        Doctor result = updatedDoctorList.get(0);
        assertEquals(result, createdDoctor);
    }

    @Test
    void testCreateDoctorNameNullThrowsError() {
        Doctor newDoctor = new Doctor();
        newDoctor.setId(1L);
        newDoctor.setName(null);
        newDoctor.setSpecialty(MedicalSpecialty.ENDOCRINOLOGY);
        newDoctor.setPhone("568894347");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            createDoctorService.create(newDoctor);
        });

        assertEquals("Doctor name is mandatory", ex.getMessage());
    }

    @Test
    void testCreateDoctorNameBlankThrowsError() {
        Doctor newDoctor = new Doctor();
        newDoctor.setId(1L);
        newDoctor.setName("");
        newDoctor.setSpecialty(MedicalSpecialty.ENDOCRINOLOGY);
        newDoctor.setPhone("568894347");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            createDoctorService.create(newDoctor);
        });

        assertEquals("Doctor name is mandatory", ex.getMessage());
    }

    @Test
    void testCreateDoctorNameWithNumbersThrowsError() {
        Doctor newDoctor = new Doctor();
        newDoctor.setId(1L);
        newDoctor.setName("1235");
        newDoctor.setSpecialty(MedicalSpecialty.ENDOCRINOLOGY);
        newDoctor.setPhone("568894347");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            createDoctorService.create(newDoctor);
        });

        assertEquals("Doctor name can not be only numbers", ex.getMessage());
    }

    @Test
    void testCreateDoctorNameWithLessThanThreeCharactersThrowsError() {
        Doctor newDoctor = new Doctor();
        newDoctor.setId(1L);
        newDoctor.setName("Ca");
        newDoctor.setSpecialty(MedicalSpecialty.ENDOCRINOLOGY);
        newDoctor.setPhone("568894347");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            createDoctorService.create(newDoctor);
        });

        assertEquals("Doctor name must have at least 3 characters", ex.getMessage());
    }
}
