package com.tatianacarvajal.medicalsystem.infrastructure.dto;

import com.tatianacarvajal.medicalsystem.domain.entities.MedicalSpecialty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DoctorDto {
    private long id;

    @NotBlank(message = "Name is mandatory")
    @Pattern(regexp = ".*[a-zA-Z].*", message = "Doctor name must contain at least one letter")
    @Size(min = 3, message = "Doctor name must have at least 3 characters")
    private String name;

    private MedicalSpecialty specialty;

    @Pattern(regexp = "\\d{9}", message = "Phone number must be 9 digits")
    private String phone;
}
