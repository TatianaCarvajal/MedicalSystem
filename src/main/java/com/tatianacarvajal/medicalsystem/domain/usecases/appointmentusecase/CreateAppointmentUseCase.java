package com.tatianacarvajal.medicalsystem.domain.usecases.appointmentusecase;

import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;

public interface CreateAppointmentUseCase {
    Appointment createAppointment(Appointment appointment);
}
