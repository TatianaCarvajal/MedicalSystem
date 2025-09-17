package com.tatianacarvajal.medicalsystem.infrastructure.controller;

import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;
import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.domain.usecases.appointment.CreateAppointmentUseCase;
import com.tatianacarvajal.medicalsystem.domain.usecases.appointment.DeleteAppointmentUseCase;
import com.tatianacarvajal.medicalsystem.domain.usecases.appointment.RetrieveAppointmentUseCase;
import com.tatianacarvajal.medicalsystem.domain.usecases.appointment.UpdateAppointmentUseCase;
import com.tatianacarvajal.medicalsystem.domain.usecases.doctor.RetrieveDoctorUseCase;
import com.tatianacarvajal.medicalsystem.domain.usecases.patient.RetrievePatientUseCase;
import com.tatianacarvajal.medicalsystem.infrastructure.dto.AppointmentDto;
import com.tatianacarvajal.medicalsystem.infrastructure.dto.AppointmentRequestDto;
import com.tatianacarvajal.medicalsystem.infrastructure.mapper.AppointmentMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Appointments", description = "Methods related to appointment")
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    @Autowired
    private CreateAppointmentUseCase createAppointmentUseCase;

    @Autowired
    private UpdateAppointmentUseCase updateAppointmentUseCase;

    @Autowired
    private RetrieveAppointmentUseCase retrieveAppointmentUseCase;

    @Autowired
    private DeleteAppointmentUseCase deleteAppointmentUseCase;

    @Autowired
    private RetrieveDoctorUseCase retrieveDoctorUseCase;

    @Autowired
    private RetrievePatientUseCase retrievePatientUseCase;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @GetMapping
    @Operation(summary = "Get all appointments")
    public ResponseEntity<List<AppointmentDto>> findAllAppointments() {
        List<Appointment> appointments = retrieveAppointmentUseCase.findAll();
        List<AppointmentDto> appointmentDtos = appointments.stream()
                .map(appointmentMapper::domainToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(appointmentDtos);
    }

    @GetMapping("/by-id")
    @Operation(summary = "Get appointment by ID")
    public ResponseEntity<AppointmentDto> findAppointmentById(@RequestHeader("X-Id") Long id) {
        Optional<Appointment> appointment = retrieveAppointmentUseCase.findById(id);
        return appointment.map(dto -> ResponseEntity.ok(appointmentMapper.domainToDto(dto)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("by-patient")
    @Operation(summary = "Get patient appointments")
    public ResponseEntity<List<AppointmentDto>> findAppointmentByPatient(@RequestHeader("X-Patient-Id") Long patientId) {
        List<Appointment> appointments = retrieveAppointmentUseCase.findAllAppointmentsOf(patientId);
        List<AppointmentDto> appointmentDtos = appointments.stream()
                .map(appointmentMapper::domainToDto)
                .toList();
        return ResponseEntity.ok(appointmentDtos);
    }

    @PostMapping
    @Operation(summary = "Create appointment")
    public ResponseEntity<AppointmentDto> createAppointment(@Valid @RequestBody AppointmentRequestDto appointmentRequestDto) {
        Optional<Doctor> doctor = retrieveDoctorUseCase.findById(appointmentRequestDto.getDoctorId());
        Optional<Patient> patient = retrievePatientUseCase.findById(appointmentRequestDto.getPatientId());

        if (doctor.isPresent() && patient.isPresent()) {
            Appointment appointment = new Appointment();
            appointment.setDoctor(doctor.get());
            appointment.setPatient(patient.get());
            appointment.setDateTime(appointmentRequestDto.getDateTime());

            Appointment createdAppointment = createAppointmentUseCase.createAppointment(appointment);
            return ResponseEntity.ok(appointmentMapper.domainToDto(createdAppointment));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    @PutMapping("/update")
    @Operation(summary = "Update appointment information")
    public ResponseEntity<AppointmentDto> updateAppointment(@Valid @RequestBody AppointmentRequestDto appointmentRequestDto) {
        Optional<Appointment> existingAppointment = retrieveAppointmentUseCase.findById(appointmentRequestDto.getId());
        if (existingAppointment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Optional<Doctor> doctor = retrieveDoctorUseCase.findById(appointmentRequestDto.getDoctorId());
        Optional<Patient> patient = retrievePatientUseCase.findById(appointmentRequestDto.getPatientId());

        if (doctor.isPresent() && patient.isPresent()) {
            Appointment appointment = existingAppointment.get();
            appointment.setDoctor(doctor.get());
            appointment.setPatient(patient.get());
            appointment.setDateTime(appointmentRequestDto.getDateTime());

            Appointment updatedAppointment = updateAppointmentUseCase.updateAppointment(appointment);
            return ResponseEntity.ok(appointmentMapper.domainToDto(updatedAppointment));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete appointment")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAppointment(@RequestHeader("X-Id") Long id) {
        deleteAppointmentUseCase.deleteAppointment(id);
    }
}
