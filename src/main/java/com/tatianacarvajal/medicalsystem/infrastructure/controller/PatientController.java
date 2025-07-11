package com.tatianacarvajal.medicalsystem.infrastructure.controller;

import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.domain.usecases.patient.CreatePatientUseCase;
import com.tatianacarvajal.medicalsystem.domain.usecases.patient.UpdatePatientUseCase;
import com.tatianacarvajal.medicalsystem.infrastructure.dto.PatientDto;
import com.tatianacarvajal.medicalsystem.infrastructure.mapper.PatientMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    @Autowired
    private CreatePatientUseCase createPatientUseCase;

    @Autowired
    private UpdatePatientUseCase updatePatientUseCase;

    @Autowired
    private PatientMapper patientMapper;

    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientDto patientDto) {
        Patient patient = patientMapper.dtoToDomain(patientDto);
        Patient createdPatient = createPatientUseCase.create(patient);
        return ResponseEntity.ok(patientMapper.domainToDto(createdPatient));
    }

    @PutMapping("/update")
    public ResponseEntity<PatientDto> updatePatient(@Valid @RequestBody PatientDto patientDto) {
        Patient patient = patientMapper.dtoToDomain(patientDto);
        Patient updatedPatient = updatePatientUseCase.update(patient);
        return ResponseEntity.ok(patientMapper.domainToDto(updatedPatient));
    }
}
