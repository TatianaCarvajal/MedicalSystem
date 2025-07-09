package com.tatianacarvajal.medicalsystem.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatientDto {
    private long id;

    @NotBlank(message = "Name is mandatory")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Patient name must only contain letters")
    @Size(min = 3, message = "Patient name must have at least 3 characters")
    private String name;

    @NotBlank(message = "Document number is mandatory")
    @Pattern(regexp = "\\d+", message = "Patient document must only contain numbers")
    @Size(min = 5, message = "Patient document must have at least 5 digits")
    private String document;

    @Pattern(regexp = "\\d{9}", message = "Phone number must be 9 digits")
    private String phone;
}
