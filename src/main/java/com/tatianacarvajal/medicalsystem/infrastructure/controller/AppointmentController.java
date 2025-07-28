package com.tatianacarvajal.medicalsystem.infrastructure.controller;

import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;
import com.tatianacarvajal.medicalsystem.domain.usecases.appointment.CreateAppointmentUseCase;
import com.tatianacarvajal.medicalsystem.infrastructure.dto.AppointmentDto;
import com.tatianacarvajal.medicalsystem.infrastructure.mapper.AppointmentMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    @Autowired
    private CreateAppointmentUseCase createAppointmentUseCase;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@Valid @RequestBody AppointmentDto appointmentDto) {
        Appointment appointment = appointmentMapper.dtoToDomain(appointmentDto);
        Appointment createdAppointment = createAppointmentUseCase.createAppointment(appointment);
        return ResponseEntity.ok(appointmentMapper.domainToDto(createdAppointment));
    }
}
