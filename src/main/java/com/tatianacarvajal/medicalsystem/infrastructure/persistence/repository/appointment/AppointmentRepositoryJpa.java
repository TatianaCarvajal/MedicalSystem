package com.tatianacarvajal.medicalsystem.infrastructure.persistence.repository.appointment;

import com.tatianacarvajal.medicalsystem.infrastructure.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AppointmentRepositoryJpa extends JpaRepository<AppointmentEntity, Long> {
    Optional<AppointmentEntity> findByDoctor_DoctorIdAndDateTime(Long doctorId, LocalDateTime dateTime);
}
