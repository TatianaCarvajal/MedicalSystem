package com.tatianacarvajal.medicalsystem.domain.usecases.appointment;

import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;

import java.util.List;
import java.util.Optional;

public interface RetrieveAppointmentUseCase {
    List<Appointment> findAll();
    Optional<Appointment> findById(Long id);
    List<Appointment> findAllAppointmentsOf(Long patientId);
}
