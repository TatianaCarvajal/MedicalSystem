package com.tatianacarvajal.medicalsystem.application.service.appointment;

import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;
import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.domain.repository.AppointmentRepository;
import com.tatianacarvajal.medicalsystem.domain.repository.DoctorRepository;
import com.tatianacarvajal.medicalsystem.domain.repository.PatientRepository;
import com.tatianacarvajal.medicalsystem.domain.usecases.appointment.UpdateAppointmentUseCase;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UpdateAppointmentService implements UpdateAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public UpdateAppointmentService(
            AppointmentRepository appointmentRepository,
            DoctorRepository doctorRepository,
            PatientRepository patientRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        Long id = appointment.getId();
        Long doctorId = appointment.getDoctor().getId();
        Long patientId = appointment.getPatient().getId();
        LocalDateTime dateTime = appointment.getDateTime();

        Appointment validatedAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment was not found with that id: " + id));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor was not found with that id: " + doctorId));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient was not found with that id: " + patientId));

        Optional<Appointment> doctorAvailability = appointmentRepository
                .findByDoctorAvailability(doctorId, dateTime);

        if (doctorAvailability.isPresent() && !doctorAvailability.get().getId().equals(id)) {
            throw new IllegalArgumentException("The doctor already has an appointment at this time.");
        }

        Optional<Appointment> patientAvailability = appointmentRepository
                .findByPatientAvailability(patientId, dateTime);

        if (patientAvailability.isPresent() && !patientAvailability.get().getId().equals(id)) {
            throw new IllegalArgumentException("The patient already has an appointment at this time.");
        }

        validatedAppointment.setDoctor(doctor);
        validatedAppointment.setPatient(patient);
        validatedAppointment.setDateTime(appointment.getDateTime());

        return appointmentRepository.update(validatedAppointment);
    }
}
