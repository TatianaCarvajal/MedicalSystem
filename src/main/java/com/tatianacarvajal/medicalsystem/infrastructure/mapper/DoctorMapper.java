package com.tatianacarvajal.medicalsystem.infrastructure.mapper;

import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.infrastructure.dto.DoctorDto;
import com.tatianacarvajal.medicalsystem.infrastructure.persistence.entity.DoctorEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    private final ModelMapper modelMapper;

    public DoctorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // Dto ↔ Domain
    public Doctor dtoToDomain(DoctorDto doctorDto) {
        return modelMapper.map(doctorDto, Doctor.class);
    }

    public DoctorDto domainToDto(Doctor doctor) {
        return modelMapper.map(doctor, DoctorDto.class);
    }

    // Entity ↔ Domain
    public Doctor entityToDomain(DoctorEntity doctorEntity) {
        return modelMapper.map(doctorEntity, Doctor.class);
    }

    public DoctorEntity domainToEntity(Doctor doctor) {
        return modelMapper.map(doctor, DoctorEntity.class);
    }
}
