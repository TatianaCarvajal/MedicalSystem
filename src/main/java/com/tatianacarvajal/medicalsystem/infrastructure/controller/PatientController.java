package com.tatianacarvajal.medicalsystem.infrastructure.controller;

import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.domain.usecases.patient.CreatePatientUseCase;
import com.tatianacarvajal.medicalsystem.domain.usecases.patient.DeletePatientUseCase;
import com.tatianacarvajal.medicalsystem.domain.usecases.patient.RetrievePatientUseCase;
import com.tatianacarvajal.medicalsystem.domain.usecases.patient.UpdatePatientUseCase;
import com.tatianacarvajal.medicalsystem.infrastructure.dto.PatientDto;
import com.tatianacarvajal.medicalsystem.infrastructure.mapper.PatientMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Tag(name = "Patients", description = "Methods related to the patient")
@RequestMapping("/api/v1/patients")
public class PatientController {

    @Autowired
    private CreatePatientUseCase createPatientUseCase;

    @Autowired
    private UpdatePatientUseCase updatePatientUseCase;

    @Autowired
    private RetrievePatientUseCase retrievePatientUseCase;

    @Autowired
    private DeletePatientUseCase deletePatientUseCase;

    @Autowired
    private PatientMapper patientMapper;

    @GetMapping("/by-id")
    @Operation(summary = "Get patient by ID")
    public ResponseEntity<PatientDto> findPatientById(@RequestHeader("X-Id") Long id) {
        Optional<Patient> patient = retrievePatientUseCase.findById(id);
        return patient.map(dto -> ResponseEntity.ok(patientMapper.domainToDto(dto)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create patient")
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientDto patientDto) {
        Patient patient = patientMapper.dtoToDomain(patientDto);
        Patient createdPatient = createPatientUseCase.create(patient);
        return ResponseEntity.ok(patientMapper.domainToDto(createdPatient));
    }

    @PutMapping("/update")
    @Operation(summary = "Update patient information")
    public ResponseEntity<PatientDto> updatePatient(@Valid @RequestBody PatientDto patientDto) {
        Patient patient = patientMapper.dtoToDomain(patientDto);
        Patient updatedPatient = updatePatientUseCase.update(patient);
        return ResponseEntity.ok(patientMapper.domainToDto(updatedPatient));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete patient")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletePatient(@RequestHeader("X-Id") Long id) {
        deletePatientUseCase.deleteById(id);
    }
}
