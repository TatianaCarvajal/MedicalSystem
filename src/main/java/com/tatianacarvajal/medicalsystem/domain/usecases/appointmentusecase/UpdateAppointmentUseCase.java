package com.tatianacarvajal.medicalsystem.domain.usecases.appointmentusecase;

import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;

public interface UpdateAppointmentUseCase {
    Appointment updateAppointment(Appointment appointment);
}
