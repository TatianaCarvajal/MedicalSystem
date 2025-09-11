package com.tatianacarvajal.medicalsystem.infrastructure.controller;

import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;
import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.domain.usecases.appointment.CreateAppointmentUseCase;
import com.tatianacarvajal.medicalsystem.domain.usecases.appointment.RetrieveAppointmentUseCase;
import com.tatianacarvajal.medicalsystem.domain.usecases.appointment.UpdateAppointmentUseCase;
import com.tatianacarvajal.medicalsystem.domain.usecases.doctor.RetrieveDoctorUseCase;
import com.tatianacarvajal.medicalsystem.domain.usecases.patient.RetrievePatientUseCase;
import com.tatianacarvajal.medicalsystem.infrastructure.dto.AppointmentDto;
import com.tatianacarvajal.medicalsystem.infrastructure.dto.AppointmentRequestDto;
import com.tatianacarvajal.medicalsystem.infrastructure.mapper.AppointmentMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    @Autowired
    private CreateAppointmentUseCase createAppointmentUseCase;

    @Autowired
    private UpdateAppointmentUseCase updateAppointmentUseCase;

    @Autowired
    private RetrieveAppointmentUseCase retrieveAppointmentUseCase;

    @Autowired
    private RetrieveDoctorUseCase retrieveDoctorUseCase;

    @Autowired
    private RetrievePatientUseCase retrievePatientUseCase;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> findAllAppointments() {
        List<Appointment> appointments = retrieveAppointmentUseCase.findAll();
        List<AppointmentDto> appointmentDtos = appointments.stream()
                .map(appointmentMapper::domainToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(appointmentDtos);
    }

    @GetMapping("/by-id")
    public ResponseEntity<AppointmentDto> findAppointmentById(@RequestHeader("X-Id") Long id) {
        Optional<Appointment> appointment = retrieveAppointmentUseCase.findById(id);
        return appointment.map(dto -> ResponseEntity.ok(appointmentMapper.domainToDto(dto)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("by-patient")
    public ResponseEntity<List<AppointmentDto>> findAppointmentByPatient(@RequestHeader("X-Patient-Id") Long patientId) {
        List<Appointment> appointments = retrieveAppointmentUseCase.findAllAppointmentsOf(patientId);
        List<AppointmentDto> appointmentDtos = appointments.stream()
                .map(appointmentMapper::domainToDto)
                .toList();
        return ResponseEntity.ok(appointmentDtos);
    }

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@Valid @RequestBody AppointmentRequestDto appointmentRequestDto) {
        Optional<Doctor> doctor = retrieveDoctorUseCase.findById(appointmentRequestDto.getDoctorId());
        Optional<Patient> patient = retrievePatientUseCase.findById(appointmentRequestDto.getPatientId());

        if(doctor.isPresent() && patient.isPresent()) {
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
    public ResponseEntity<AppointmentDto> updateAppointment(@Valid @RequestBody AppointmentRequestDto appointmentRequestDto) {
        Optional<Appointment> existingAppointment = retrieveAppointmentUseCase.findById(appointmentRequestDto.getId());
        if (existingAppointment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Optional<Doctor> doctor = retrieveDoctorUseCase.findById(appointmentRequestDto.getDoctorId());
        Optional<Patient> patient = retrievePatientUseCase.findById(appointmentRequestDto.getPatientId());

        if(doctor.isPresent() && patient.isPresent()) {
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
}
