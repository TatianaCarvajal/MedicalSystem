package com.tatianacarvajal.medicalsystem.service.appointment;

import com.tatianacarvajal.medicalsystem.application.service.appointment.DeleteAppointmentService;
import com.tatianacarvajal.medicalsystem.domain.entities.Appointment;
import com.tatianacarvajal.medicalsystem.domain.repository.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteAppointmentServiceTests {

    @Mock
    AppointmentRepository appointmentRepository;

    @InjectMocks
    DeleteAppointmentService deleteAppointmentService;

    Appointment appointment;

    @BeforeEach
    void setup() {
        appointment = new Appointment();
        appointment.setId(1L);
    }

    @Test
    void testDeleteAppointment() {
        long id = appointment.getId();

        when(appointmentRepository.findById(id))
                .thenReturn(Optional.of(appointment))
                .thenReturn(Optional.empty());

        deleteAppointmentService.deleteAppointment(id);

        Optional<Appointment> appointmentResult = appointmentRepository.findById(id);
        assertTrue(appointmentResult.isEmpty());

        verify(appointmentRepository, times(2)).findById(id);
        verify(appointmentRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteAppointmentFailure() {
        long id = 56L;

        when(appointmentRepository.findById(id)).thenReturn(Optional.empty());

        Exception ex = assertThrows(EntityNotFoundException.class, () -> {
            deleteAppointmentService.deleteAppointment(id);
        });

        assertEquals("Appointment was not found with that id: " + id, ex.getMessage());

        verify(appointmentRepository, times(1)).findById(id);
        verify(appointmentRepository, never()).deleteById(any());
    }
}
