package com.tatianacarvajal.medicalsystem.infrastructure.mapper;

import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;
import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.infrastructure.dto.AppointmentDto;
import com.tatianacarvajal.medicalsystem.infrastructure.dto.DoctorDto;
import com.tatianacarvajal.medicalsystem.infrastructure.dto.PatientDto;
import com.tatianacarvajal.medicalsystem.infrastructure.persistence.entity.AppointmentEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    private final ModelMapper modelMapper;

    public AppointmentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.addMappings(new PropertyMap<Appointment, AppointmentDto>() {
            @Override
            protected void configure() {
                map(source.getDoctor(), destination.getDoctor());
                map(source.getPatient(), destination.getPatient());
            }
        });

        modelMapper.addMappings(new PropertyMap<Doctor, DoctorDto>() {
            @Override
            protected void configure() {
                map(source.getId(), destination.getId());
                map(source.getName(), destination.getName());
                map(source.getSpecialty(), destination.getSpecialty());
                map(source.getPhone(), destination.getPhone());
            }
        });

        modelMapper.addMappings(new PropertyMap<Patient, PatientDto>() {
            @Override
            protected void configure() {
                map(source.getId(), destination.getId());
                map(source.getName(), destination.getName());
                map(source.getDocument(), destination.getDocument());
                map(source.getPhone(), destination.getPhone());
            }
        });
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
