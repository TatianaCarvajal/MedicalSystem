package com.tatianacarvajal.medicalsystem.infrastructure.controller;

import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;
import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.domain.usecases.appointment.CreateAppointmentUseCase;
import com.tatianacarvajal.medicalsystem.domain.usecases.doctor.RetrieveDoctorUseCase;
import com.tatianacarvajal.medicalsystem.domain.usecases.patient.RetrievePatientUseCase;
import com.tatianacarvajal.medicalsystem.infrastructure.dto.AppointmentDto;
import com.tatianacarvajal.medicalsystem.infrastructure.dto.AppointmentRequestDto;
import com.tatianacarvajal.medicalsystem.infrastructure.mapper.AppointmentMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    @Autowired
    private CreateAppointmentUseCase createAppointmentUseCase;

    @Autowired
    private RetrieveDoctorUseCase retrieveDoctorUseCase;

    @Autowired
    private RetrievePatientUseCase retrievePatientUseCase;

    @Autowired
    private AppointmentMapper appointmentMapper;

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
}
