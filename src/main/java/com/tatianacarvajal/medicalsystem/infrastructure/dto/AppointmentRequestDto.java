package com.tatianacarvajal.medicalsystem.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequestDto {
    private long id;

    @NotNull(message = "Doctor is mandatory")
    @JsonProperty("doctor_id")
    private long doctorId;

    @NotNull(message = "Patient is mandatory")
    @JsonProperty("patient_id")
    private long patientId;

    @NotNull(message = "Date is mandatory")
    @Future(message = "Appointment date must be in the future")
    private LocalDateTime dateTime;
}
