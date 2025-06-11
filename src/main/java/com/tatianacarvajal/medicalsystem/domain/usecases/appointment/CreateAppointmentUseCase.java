package com.tatianacarvajal.medicalsystem.domain.usecases.appointment;

import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;

public interface CreateAppointmentUseCase {
    Appointment createAppointment(Appointment appointment);
}
