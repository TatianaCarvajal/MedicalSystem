package com.tatianacarvajal.medicalsystem.infrastructure.dto;

import com.tatianacarvajal.medicalsystem.domain.entities.MedicalSpecialty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DoctorDto {
    private long id;
   // @NotBlank(message = "Name is mandatory")
    private String name;
    // @NotNull(message = "Specialty is mandatory")
    private MedicalSpecialty specialty;
    private String phone;
}
