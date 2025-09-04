package com.tatianacarvajal.medicalsystem.service.appointment;

import com.tatianacarvajal.medicalsystem.application.service.appointment.UpdateAppointmentService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateAppointmentServiceTests {

    @Mock
    AppointmentRepository appointmentRepository;

    @Mock
    DoctorRepository doctorRepository;

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    UpdateAppointmentService updateAppointmentService;

    private Doctor doctor;
    private Patient patient;
    private LocalDateTime dateTime;
    private Appointment appointment;

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

        dateTime = LocalDateTime.of(2025, 8, 24, 10, 40);

        appointment = new Appointment();
        appointment.setId(2L);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDateTime(dateTime);
    }

    @Test
    void testUpdateAppointment() {
        Doctor updatedDoctor = new Doctor();
        updatedDoctor.setId(doctor.getId());
        updatedDoctor.setName("Juan");
        updatedDoctor.setSpecialty(MedicalSpecialty.NEUROLOGY);
        updatedDoctor.setPhone("568894347");

        Patient updatedPatient = new Patient();
        updatedPatient.setId(patient.getId());
        updatedPatient.setName("Carl");
        updatedPatient.setDocument("12345");
        updatedPatient.setPhone("320630289");

        LocalDateTime updatedDateTime = LocalDateTime.of(2025, 8, 25, 10, 40);

        Appointment updatedAppointment = new Appointment();
        updatedAppointment.setId(appointment.getId());
        updatedAppointment.setDoctor(updatedDoctor);
        updatedAppointment.setPatient(updatedPatient);
        updatedAppointment.setDateTime(updatedDateTime);

        when(appointmentRepository.findById(2L)).thenReturn(Optional.of(appointment));
        when(doctorRepository.findById(2L)).thenReturn(Optional.of(updatedDoctor));
        when(patientRepository.findById(4L)).thenReturn(Optional.of(updatedPatient));
        when(appointmentRepository.update(any(Appointment.class))).thenReturn(updatedAppointment);

        Appointment appointmentResult = updateAppointmentService.updateAppointment(updatedAppointment);

        assertNotNull(appointmentResult);
        assertEquals(updatedDoctor.getName(), appointmentResult.getDoctor().getName());
        assertEquals(updatedPatient.getDocument(), appointmentResult.getPatient().getDocument());
        assertEquals(updatedDateTime, appointmentResult.getDateTime());
        verify(appointmentRepository, times(1)).update(any(Appointment.class));
    }

    @Test
    void testAppointmentNotFound() {
        appointment.setId(6L);
        when(appointmentRepository.findById(6L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(EntityNotFoundException.class, () -> {
            updateAppointmentService.updateAppointment(appointment);
        });

        assertEquals("Appointment was not found with that id: 6", ex.getMessage());
    }

    @Test
    void testDoctorNotFound() {
        doctor.setId(3L);
        appointment.setId(4L);

        when(appointmentRepository.findById(4L)).thenReturn(Optional.of(appointment));
        when(doctorRepository.findById(3L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(EntityNotFoundException.class, () -> {
            updateAppointmentService.updateAppointment(appointment);
        });

        assertEquals("Doctor was not found with that id: 3", ex.getMessage());
    }

    @Test
    void testPatientNotFound() {
        patient.setId(9L);
        appointment.setId(4L);
        doctor.setId(1L);

        when(appointmentRepository.findById(4L)).thenReturn(Optional.of(appointment));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(9L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(EntityNotFoundException.class, () -> {
            updateAppointmentService.updateAppointment(appointment);
        });

        assertEquals("Patient was not found with that id: 9", ex.getMessage());
    }

    @Test
    void testUpdateAppointmentThrowsErrorWhenDoctorIsAlreadyOccupied() {
        LocalDateTime appointmentDate = LocalDateTime.now().plusDays(1);
        appointment.setDateTime(appointmentDate);

        Appointment conflictingAppointment = new Appointment();
        conflictingAppointment.setId(20L);
        conflictingAppointment.setDoctor(doctor);
        conflictingAppointment.setPatient(patient);
        conflictingAppointment.setDateTime(appointmentDate);

        when(appointmentRepository.findById(appointment.getId())).thenReturn(Optional.of(appointment));
        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));
        when(appointmentRepository.findByDoctorAvailability(doctor.getId(), appointmentDate))
                .thenReturn(Optional.of(conflictingAppointment));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            updateAppointmentService.updateAppointment(appointment);
        });

        assertEquals("The doctor already has an appointment at this time.", ex.getMessage());
    }

    @Test
    void testUpdateAppointmentThrowsErrorWhenPatientIsAlreadyOccupied() {
        LocalDateTime appointmentDate = LocalDateTime.now().plusDays(1);
        appointment.setDateTime(appointmentDate);

        Appointment conflictingAppointment = new Appointment();
        conflictingAppointment.setId(20L);
        conflictingAppointment.setDoctor(doctor);
        conflictingAppointment.setPatient(patient);
        conflictingAppointment.setDateTime(appointmentDate);

        when(appointmentRepository.findById(appointment.getId())).thenReturn(Optional.of(appointment));
        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));
        when(appointmentRepository.findByPatientAvailability(patient.getId(), appointmentDate))
                .thenReturn(Optional.of(conflictingAppointment));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            updateAppointmentService.updateAppointment(appointment);
        });

        assertEquals("The patient already has an appointment at this time.", ex.getMessage());
    }
}
