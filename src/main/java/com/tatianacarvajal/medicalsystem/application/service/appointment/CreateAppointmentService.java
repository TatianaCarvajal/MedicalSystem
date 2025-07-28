package com.tatianacarvajal.medicalsystem.application.service.appointment;

import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;
import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.domain.repository.AppointmentRepository;
import com.tatianacarvajal.medicalsystem.domain.repository.DoctorRepository;
import com.tatianacarvajal.medicalsystem.domain.repository.PatientRepository;
import com.tatianacarvajal.medicalsystem.domain.usecases.appointment.CreateAppointmentUseCase;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateAppointmentService implements CreateAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public CreateAppointmentService(
            AppointmentRepository appointmentRepository,
            DoctorRepository doctorRepository,
            PatientRepository patientRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        Long doctorId = appointment.getDoctor().getId();
        Long patientId = appointment.getPatient().getId();
        LocalDateTime dateTime = appointment.getDateTime();

        Doctor validatedDoctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor was not found with that id: " + doctorId));

        Patient validatedPatient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient was not found with that id: " + patientId));

        if (dateTime == null || dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Appointment date must not be null and have to be in the future");
        }
        appointment.setDoctor(validatedDoctor);
        appointment.setPatient(validatedPatient);
        appointment.setDateTime(dateTime);

        return appointmentRepository.create(appointment);

    }
}
