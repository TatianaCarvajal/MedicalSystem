package com.tatianacarvajal.medicalsystem.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "patients")
public class PatientEntity {

    @Id
    @Column(name = "patient_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long patientId;

    @Column(name = "name")
    private String name;

    @Column(name = "identification_number")
    private String document;

    @Column(name = "phone")
    private String phone;
}
