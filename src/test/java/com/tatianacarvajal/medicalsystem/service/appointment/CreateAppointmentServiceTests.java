package com.tatianacarvajal.medicalsystem.service.appointment;

import com.tatianacarvajal.medicalsystem.application.service.appointment.CreateAppointmentService;
import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;
import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.entities.MedicalSpecialty;
import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.domain.repository.AppointmentRepository;
import com.tatianacarvajal.medicalsystem.domain.repository.DoctorRepository;
import com.tatianacarvajal.medicalsystem.domain.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateAppointmentServiceTests {

    @Mock
    AppointmentRepository appointmentRepository;

    @Mock
    DoctorRepository doctorRepository;

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    CreateAppointmentService createAppointmentService;

    private Doctor doctor;
    private Patient patient;
    private LocalDateTime dateTime;
    private Appointment appointment;

    @BeforeEach
    void setupTest() {
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

        dateTime = LocalDateTime.of(2025, 8, 24, 10, 40);

        appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDateTime(dateTime);
    }

    @Test
    void testCreateAppointment() {
        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));
        when(appointmentRepository.create(any(Appointment.class))).thenReturn(appointment);

        Appointment appointmentResult = createAppointmentService.createAppointment(appointment);

        assertNotNull(appointmentResult);
        assertEquals(doctor, appointmentResult.getDoctor());
        assertEquals(patient, appointmentResult.getPatient());
        assertEquals(dateTime, appointmentResult.getDateTime());

        verify(doctorRepository, times(1)).findById(doctor.getId());
        verify(patientRepository, times(1)).findById(patient.getId());
        verify(appointmentRepository, times(1)).create(appointment);
    }

    @Test
    void testDoctorNotFound() {
        doctor.setId(5L);
        when(doctorRepository.findById(5L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(EntityNotFoundException.class, () -> {
            createAppointmentService.createAppointment(appointment);
        });

        assertEquals("Doctor was not found with that id: 5", ex.getMessage());
    }

    @Test
    void testPatientNotFound() {
        doctor.setId(1L);
        patient.setId(3L);
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(3L)).thenReturn(Optional.empty());

        appointment.setPatient(patient);

        Exception ex = assertThrows(EntityNotFoundException.class, () -> {
            createAppointmentService.createAppointment(appointment);
        });

        assertEquals("Patient was not found with that id: 3", ex.getMessage());
    }

    @Test
    void testCreateAppointmentThrowsErrorWhenDateIsNull() {
        doctor.setId(1L);
        patient.setId(2L);
        appointment.setDateTime(null);

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(2L)).thenReturn(Optional.of(patient));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            createAppointmentService.createAppointment(appointment);
        });

        assertEquals("Appointment date must not be null and have to be in the future", ex.getMessage());
    }

    @Test
    void testCreateAppointmentThrowsErrorWhenDateIsInThePast() {
        doctor.setId(1L);
        patient.setId(2L);
        appointment.setDateTime(LocalDateTime.now().minusDays(1));

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(2L)).thenReturn(Optional.of(patient));

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                createAppointmentService.createAppointment(appointment)
        );

        assertEquals("Appointment date must not be null and have to be in the future", ex.getMessage());
    }

    @Test
    void testCreateAppointmentThrowsErrorWhenDoctorIsAlreadyOccupied() {
        doctor.setId(1L);
        patient.setId(2L);
        appointment.setDateTime(LocalDateTime.now().plusDays(1));

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(2L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findByDoctorAvailability(1L, appointment.getDateTime()))
                .thenReturn(Optional.of(new Appointment()));

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                createAppointmentService.createAppointment(appointment)
        );

        assertEquals("The doctor already has an appointment at this time.", ex.getMessage());
    }
}
