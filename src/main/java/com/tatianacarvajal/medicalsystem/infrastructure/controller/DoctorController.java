package com.tatianacarvajal.medicalsystem.infrastructure.controller;

import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.usecases.doctor.CreateDoctorUseCase;
import com.tatianacarvajal.medicalsystem.domain.usecases.doctor.UpdateDoctorUseCase;
import com.tatianacarvajal.medicalsystem.infrastructure.dto.DoctorDto;
import com.tatianacarvajal.medicalsystem.infrastructure.mapper.DoctorMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    @Autowired
    private CreateDoctorUseCase createDoctorUseCase;

    @Autowired
    private UpdateDoctorUseCase updateDoctorUseCase;

    @Autowired
    private DoctorMapper doctorMapper;

    @PostMapping
    public ResponseEntity<DoctorDto> createDoctor(@Valid @RequestBody DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.dtoToDomain(doctorDto);
        Doctor createdDoctor = createDoctorUseCase.create(doctor);
        return ResponseEntity.ok(doctorMapper.domainToDto(createdDoctor));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@Valid @RequestBody DoctorDto doctorDto, @PathVariable Long id) {
        doctorDto.setId(id);
        Doctor doctor = doctorMapper.dtoToDomain(doctorDto);
        Doctor updatedDoctor = updateDoctorUseCase.update(doctor);
        return ResponseEntity.ok(doctorMapper.domainToDto(updatedDoctor));
    }
}
