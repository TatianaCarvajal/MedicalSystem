package com.tatianacarvajal.medicalsystem.infrastructure.controller;

import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.entities.MedicalSpecialty;
import com.tatianacarvajal.medicalsystem.domain.usecases.doctor.CreateDoctorUseCase;
import com.tatianacarvajal.medicalsystem.domain.usecases.doctor.DeleteDoctorUseCase;
import com.tatianacarvajal.medicalsystem.domain.usecases.doctor.RetrieveDoctorUseCase;
import com.tatianacarvajal.medicalsystem.domain.usecases.doctor.UpdateDoctorUseCase;
import com.tatianacarvajal.medicalsystem.infrastructure.dto.DoctorDto;
import com.tatianacarvajal.medicalsystem.infrastructure.mapper.DoctorMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Doctors", description = "Methods related to the doctor")
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    @Autowired
    private CreateDoctorUseCase createDoctorUseCase;

    @Autowired
    private UpdateDoctorUseCase updateDoctorUseCase;

    @Autowired
    private RetrieveDoctorUseCase retrieveDoctorUseCase;

    @Autowired
    private DeleteDoctorUseCase deleteDoctorUseCase;

    @Autowired
    private DoctorMapper doctorMapper;

    @GetMapping("/by-id")
    @Operation(summary = "Get doctor by ID")
    public ResponseEntity<DoctorDto> findDoctorById(@RequestHeader("X-Id") Long id) {
        Optional<Doctor> doctor = retrieveDoctorUseCase.findById(id);
        return doctor.map(dto -> ResponseEntity.ok(doctorMapper.domainToDto(dto)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-specialty")
    @Operation(summary = "Get doctor by medical specialty")
    public ResponseEntity<List<DoctorDto>> findDoctorsBySpecialty(@RequestHeader("X-Medical-Specialty") String specialtyHeader) {
        MedicalSpecialty medicalSpecialty = MedicalSpecialty.valueOf(specialtyHeader.toUpperCase());
        List<Doctor> doctors = retrieveDoctorUseCase.findBySpecialty(medicalSpecialty);
        List<DoctorDto> doctorDtos = doctors.stream().map(doctorMapper::domainToDto).toList();
        return ResponseEntity.ok(doctorDtos);
    }

    @PostMapping
    @Operation(summary = "Create doctor")
    public ResponseEntity<DoctorDto> createDoctor(@Valid @RequestBody DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.dtoToDomain(doctorDto);
        Doctor createdDoctor = createDoctorUseCase.create(doctor);
        return ResponseEntity.ok(doctorMapper.domainToDto(createdDoctor));
    }

    @PutMapping("/update")
    @Operation(summary = "Update doctor information")
    public ResponseEntity<DoctorDto> updateDoctor(
            @Valid @RequestBody DoctorDto doctorDto
    ) {
        Doctor doctor = doctorMapper.dtoToDomain(doctorDto);
        Doctor updatedDoctor = updateDoctorUseCase.update(doctor);
        return ResponseEntity.ok(doctorMapper.domainToDto(updatedDoctor));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete doctor")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteDoctor(@RequestHeader("X-Id") Long id) {
        deleteDoctorUseCase.deleteById(id);
    }
}
