package com.tatianacarvajal.medicalsystem.infrastructure.persistence.entity;

import com.tatianacarvajal.medicalsystem.domain.entities.MedicalSpecialty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "doctors")
public class DoctorEntity {

    @Id
    @Column(name = "doctor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long doctorId;

    @Column(name = "name")
    private String name;

    @Column(name = "specialty")
    @Enumerated(EnumType.STRING)
    private MedicalSpecialty medicalSpecialty;

    @Column(name = "phone")
    private String phone;
}
