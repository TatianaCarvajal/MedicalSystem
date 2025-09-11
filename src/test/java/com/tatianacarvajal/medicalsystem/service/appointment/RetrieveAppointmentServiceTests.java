package com.tatianacarvajal.medicalsystem.service.appointment;

import com.tatianacarvajal.medicalsystem.application.service.appointment.RetrieveAppointmentService;
import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;
import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.entities.MedicalSpecialty;
import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.domain.repository.AppointmentRepository;
import com.tatianacarvajal.medicalsystem.domain.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RetrieveAppointmentServiceTests {

    @Mock
    AppointmentRepository appointmentRepository;

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    RetrieveAppointmentService retrieveAppointmentService;

    Appointment appointment;
    Doctor doctor;
    Patient patient;

    @BeforeEach
    void setupAppointment() {
        doctor = new Doctor();
        doctor.setId(2L);
        doctor.setName("Sara");
        doctor.setSpecialty(MedicalSpecialty.ENDOCRINOLOGY);
        doctor.setPhone("568894347");

        patient = new Patient();
        patient.setId(4L);
        patient.setName("Luis");
        patient.setDocument("12345");
        patient.setPhone("320630289");

        appointment = new Appointment();
        appointment.setId(3L);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDateTime(LocalDateTime.now().plusDays(1));
    }

    @Test
    void testFindAllAppointments() {
        when(appointmentRepository.findAll()).thenReturn(List.of(appointment));

        List<Appointment> appointmentList = retrieveAppointmentService.findAll();

        assertNotNull(appointmentList);
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    void testFindAllAppointmentsFailure() {
        when(appointmentRepository.findAll()).thenReturn(Collections.emptyList());

        Exception ex = assertThrows(EntityNotFoundException.class, () -> {
            retrieveAppointmentService.findAll();
        });

        assertEquals("No appointments found.", ex.getMessage());
    }

    @Test
    void testFindAppointmentById() {
        when(appointmentRepository.findById(appointment.getId())).thenReturn(Optional.of(appointment));

        Optional<Appointment> appointmentResult = retrieveAppointmentService.findById(appointment.getId());

        assertNotNull(appointmentResult);
        assertEquals(appointment.getId(), appointmentResult.get().getId());
    }

    @Test
    void testFindAppointmentByIdFailure() {
        when(appointmentRepository.findById(50L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(EntityNotFoundException.class, () -> {
            retrieveAppointmentService.findById(50L);
        });

        assertEquals("Appointment was not found with that id: 50", ex.getMessage());
    }

    @Test
    void testFindAppointmentsByPatient() {
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));
        when(appointmentRepository.findAppointmentsByPatientId(patient.getId())).thenReturn(List.of(appointment));

        List<Appointment> patientAppointments = retrieveAppointmentService.findAllAppointmentsOf(patient.getId());

        assertNotNull(patientAppointments);
        assertFalse(patientAppointments.isEmpty());
        assertEquals(patient.getId(), patientAppointments.get(0).getPatient().getId());
    }

    @Test
    void testPatientNotFound() {
        patient.setId(9L);

        when(patientRepository.findById(9L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(EntityNotFoundException.class, () -> {
            retrieveAppointmentService.findAllAppointmentsOf(patient.getId());
        });

        assertEquals("Patient was not found with that id: 9", ex.getMessage());
    }

    @Test
    void testFindAppointmentsByPatientThrowsExceptionWhenNoAppointmentsExist() {
        Long patientId = patient.getId();

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findAppointmentsByPatientId(patientId)).thenReturn(Collections.emptyList());

        Exception ex = assertThrows(EntityNotFoundException.class, () -> {
            retrieveAppointmentService.findAllAppointmentsOf(patientId);
        });

        assertEquals("No appointments found for patient with id: " + patientId, ex.getMessage());

        verify(patientRepository).findById(patientId);
        verify(appointmentRepository).findAppointmentsByPatientId(patientId);
    }
}
