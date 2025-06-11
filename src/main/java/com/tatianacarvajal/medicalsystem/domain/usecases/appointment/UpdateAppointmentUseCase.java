package com.tatianacarvajal.medicalsystem.domain.usecases.appointment;

import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;

public interface UpdateAppointmentUseCase {
    Appointment updateAppointment(Appointment appointment);
}
