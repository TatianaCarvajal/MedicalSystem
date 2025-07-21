package com.tatianacarvajal.medicalsystem.infrastructure.mapper;

import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;
import com.tatianacarvajal.medicalsystem.infrastructure.dto.AppointmentDto;
import com.tatianacarvajal.medicalsystem.infrastructure.persistence.entity.AppointmentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    private final ModelMapper modelMapper;

    public AppointmentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // Dto ↔ Domain
    public Appointment dtoToDomain(AppointmentDto appointmentDto) {
        return modelMapper.map(appointmentDto, Appointment.class);
    }

    public AppointmentDto domainToDto(Appointment appointment) {
        return modelMapper.map(appointment, AppointmentDto.class);
    }

    // Entity ↔ Domain
    public Appointment entityToDomain(AppointmentEntity appointmentEntity) {
        return modelMapper.map(appointmentEntity, Appointment.class);
    }

    public AppointmentEntity domainToEntity(Appointment appointment) {
        return modelMapper.map(appointment, AppointmentEntity.class);
    }
}
