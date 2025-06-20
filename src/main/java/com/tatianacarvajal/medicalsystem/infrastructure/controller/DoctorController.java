package com.tatianacarvajal.medicalsystem.infrastructure.controller;

import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.usecases.doctor.CreateDoctorUseCase;
import com.tatianacarvajal.medicalsystem.infrastructure.dto.DoctorDto;
import com.tatianacarvajal.medicalsystem.infrastructure.mapper.DoctorMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    @Autowired
    private CreateDoctorUseCase createDoctorUseCase;

    @Autowired
    private DoctorMapper doctorMapper;

    @PostMapping
    public ResponseEntity<DoctorDto> createDoctor(@Valid @RequestBody DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.dtoToDomain(doctorDto);
        Doctor createdDoctor = createDoctorUseCase.create(doctor);
        return ResponseEntity.ok(doctorMapper.domainToDto(createdDoctor));
    }
}
