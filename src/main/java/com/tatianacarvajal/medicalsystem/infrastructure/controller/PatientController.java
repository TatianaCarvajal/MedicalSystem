package com.tatianacarvajal.medicalsystem.infrastructure.controller;

import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.domain.usecases.patient.CreatePatientUseCase;
import com.tatianacarvajal.medicalsystem.infrastructure.dto.PatientDto;
import com.tatianacarvajal.medicalsystem.infrastructure.mapper.PatientMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    @Autowired
    private CreatePatientUseCase createPatientUseCase;

    @Autowired
    private PatientMapper patientMapper;

    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientDto patientDto) {
        Patient patient = patientMapper.dtoToDomain(patientDto);
        Patient createdPatient = createPatientUseCase.create(patient);
        return ResponseEntity.ok(patientMapper.domainToDto(createdPatient));
    }
}
