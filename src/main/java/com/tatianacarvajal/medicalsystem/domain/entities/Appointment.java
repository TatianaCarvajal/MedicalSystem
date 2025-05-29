package com.tatianacarvajal.medicalsystem.domain.entities;

import java.time.LocalDateTime;

public class Appointment {
    private Long id;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime dateTime;

    private Appointment(Long id, Doctor doctor, Patient patient, LocalDateTime dateTime) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.dateTime = dateTime;
    }

    public static Appointment createAppointment(Long id, Doctor doctor, Patient patient, LocalDateTime dateTime) {
        if (doctor == null) throw new IllegalArgumentException("Doctor must not be null");
        if (patient == null) throw new IllegalArgumentException("Patient must not be null");
        if (dateTime == null || dateTime.isBefore(LocalDateTime.now())) {
            throw  new IllegalArgumentException("Appointment date must not be null and have to be in the future");
        }
        return new Appointment(id, doctor, patient, dateTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
