package com.tatianacarvajal.medicalsystem.infrastructure.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {
    private long id;

    @NotNull(message = "Doctor is mandatory")
    @Valid
    private DoctorDto doctor;

    @NotNull(message = "Patient is mandatory")
    @Valid
    private PatientDto patient;

    @NotNull(message = "Date is mandatory")
    @Future(message = "Appointment date must be in the future")
    private LocalDateTime dateTime;
}
