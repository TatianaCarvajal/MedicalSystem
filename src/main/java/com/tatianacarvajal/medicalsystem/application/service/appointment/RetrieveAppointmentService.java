package com.tatianacarvajal.medicalsystem.application.service.appointment;

import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;
import com.tatianacarvajal.medicalsystem.domain.repository.AppointmentRepository;
import com.tatianacarvajal.medicalsystem.domain.usecases.appointment.RetrieveAppointmentUseCase;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RetrieveAppointmentService implements RetrieveAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;

    public RetrieveAppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Appointment> findAll() {
        return List.of();
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        Appointment validatedAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment was not found with that id: " + id));
        return appointmentRepository.findById(id);
    }

    @Override
    public List<Appointment> findAllAppointmentsOf(Long patientId) {
        return List.of();
    }
}
