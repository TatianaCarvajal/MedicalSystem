package com.tatianacarvajal.medicalsystem.infrastructure.persistence.repository.appointment;

import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;
import com.tatianacarvajal.medicalsystem.domain.repository.AppointmentRepository;
import com.tatianacarvajal.medicalsystem.infrastructure.mapper.AppointmentMapper;
import com.tatianacarvajal.medicalsystem.infrastructure.persistence.entity.AppointmentEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AppointmentRepositoryImpl implements AppointmentRepository {

    private final AppointmentRepositoryJpa appointmentRepositoryJpa;
    private final AppointmentMapper appointmentMapper;

    public AppointmentRepositoryImpl(AppointmentRepositoryJpa appointmentRepositoryJpa, AppointmentMapper appointmentMapper) {
        this.appointmentRepositoryJpa = appointmentRepositoryJpa;
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    public Appointment create(Appointment appointment) {
        AppointmentEntity appointmentEntity = appointmentMapper.domainToEntity(appointment);
        return appointmentMapper.entityToDomain(appointmentRepositoryJpa.save(appointmentEntity));
    }

    @Override
    public List<Appointment> findAll() {
        return List.of();
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Appointment> findAllAppointmentsOf(Long patientId) {
        return List.of();
    }

    @Override
    public Appointment update(Appointment appointment) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
