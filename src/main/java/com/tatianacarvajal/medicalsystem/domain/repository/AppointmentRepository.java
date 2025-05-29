package com.tatianacarvajal.medicalsystem.domain.repository;

import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    Appointment create(Appointment appointment);
    List<Appointment> findAll();
    Optional<Appointment> findById(Long id);
    List<Appointment> findAllAppointmentsOf(Long patientId);
    Appointment update(Appointment appointment);
    void deleteById(Long id);
}
