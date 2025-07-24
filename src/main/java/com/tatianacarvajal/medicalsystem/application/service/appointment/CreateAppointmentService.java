package com.tatianacarvajal.medicalsystem.application.service.appointment;

import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;
import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.domain.repository.AppointmentRepository;
import com.tatianacarvajal.medicalsystem.domain.usecases.appointment.CreateAppointmentUseCase;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateAppointmentService implements CreateAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;
    private Doctor doctor;
    private Patient patient;

    public CreateAppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        Long doctorId = doctor.getId();
        Long patientId = patient.getId();
        LocalDateTime dateTime = LocalDateTime.from(LocalDate.now());

        if (doctor == null) throw new IllegalArgumentException("Doctor must not be null");
        if (patient == null) throw new IllegalArgumentException("Patient must not be null");
        if (dateTime == null || dateTime.isBefore(LocalDateTime.now())) {
            throw  new IllegalArgumentException("Appointment date must not be null and have to be in the future");
        }
        return appointment;

    }
}
