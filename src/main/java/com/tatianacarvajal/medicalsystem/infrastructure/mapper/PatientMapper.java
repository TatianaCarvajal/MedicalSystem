package com.tatianacarvajal.medicalsystem.infrastructure.mapper;

import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.infrastructure.dto.PatientDto;
import com.tatianacarvajal.medicalsystem.infrastructure.persistence.entity.PatientEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    private final ModelMapper modelMapper;

    public PatientMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // Dto ↔ Domain
    public Patient dtoToDomain(PatientDto patientDto) {
        return modelMapper.map(patientDto, Patient.class);
    }

    public PatientDto domainToDto(Patient patient) {
        return modelMapper.map(patient, PatientDto.class);
    }

    // Entity ↔ Domain
    public Patient entityToDomain(PatientEntity patientEntity) {
        return modelMapper.map(patientEntity, Patient.class);
    }

    public PatientEntity domainToEntity(Patient patient) {
        return modelMapper.map(patient, PatientEntity.class);
    }
}
