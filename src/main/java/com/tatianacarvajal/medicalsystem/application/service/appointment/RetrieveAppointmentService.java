package com.tatianacarvajal.medicalsystem.application.service.appointment;

import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;
import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.domain.repository.AppointmentRepository;
import com.tatianacarvajal.medicalsystem.domain.repository.PatientRepository;
import com.tatianacarvajal.medicalsystem.domain.usecases.appointment.RetrieveAppointmentUseCase;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RetrieveAppointmentService implements RetrieveAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public RetrieveAppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> appointments = appointmentRepository.findAll();
        if (appointments.isEmpty()) {
            throw new EntityNotFoundException("No appointments found.");
        }
        return appointments;
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id)
                .map(Optional::of)
                .orElseThrow(() -> new EntityNotFoundException("Appointment was not found with that id: " + id));
    }

    @Override
    public List<Appointment> findAllAppointmentsOf(Long patientId) {
        patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient was not found with that id: " + patientId));

        List<Appointment> appointments = appointmentRepository.findAppointmentsByPatientId(patientId);

        if (appointments.isEmpty()) {
            throw new EntityNotFoundException("No appointments found for patient with id: " + patientId);
        }

        return appointments;
    }
}
