package com.Task.MiniProject_2.serviceTest;

import com.Task.MiniProject_2.entity.Appointment;
import com.Task.MiniProject_2.entity.Doctor;
import com.Task.MiniProject_2.exception.AppointmentNotFoundException;
import com.Task.MiniProject_2.repository.AppointmentRepository;
import com.Task.MiniProject_2.service.AppointmentService;
import com.Task.MiniProject_2.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepo;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private Doctor doctor;
    private Appointment appointment;

    @BeforeEach
    void setup() {
        doctor = new Doctor(1L, "Dr. Smith", "Cardiology", 500.0, "10:00 AM - 12:00 PM", "Monday to Friday");
        appointment = new Appointment(
                1L,
                "John Doe",
                30,
                "Male",
                "john.doe@example.com",
                "1234567890",
                LocalDate.now(),
                "Regular Checkup",
                doctor
        );
    }

    @Test
    void testDisplayAll() {
        when(appointmentRepo.findAll()).thenReturn(Arrays.asList(appointment));

        List<Appointment> appointments = appointmentService.displayAll();

        assertNotNull(appointments);
        assertEquals(1, appointments.size());
        assertEquals("John Doe", appointments.get(0).getPatientName());
        verify(appointmentRepo, times(1)).findAll();
    }

    @Test
    void testAddAppointment() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        appointmentService.addAppointment(appointment);

        assertEquals("John Doe", appointment.getPatientName());
        assertEquals("Cardiology", appointment.getDoctor().getSpecialization());
        verify(doctorRepository, times(1)).findById(1L);
        verify(appointmentRepo, times(1)).save(appointment);
    }

    @Test
    void testSearchAppointmentsByPatientName() {
        when(appointmentRepo.findByPatientNameIgnoreCase("john doe")).thenReturn(Arrays.asList(appointment));

        List<Appointment> appointments = appointmentService.searchAppointmentsByPatientName("john doe");

        assertNotNull(appointments);
        assertEquals(1, appointments.size());
        assertEquals("John Doe", appointments.get(0).getPatientName());
        verify(appointmentRepo, times(1)).findByPatientNameIgnoreCase("john doe");
    }

    @Test
    void testSearchAppointmentsByDoctorName() {
        when(appointmentRepo.findByDoctorNameContainingIgnoreCase("smith")).thenReturn(Arrays.asList(appointment));

        List<Appointment> appointments = appointmentService.searchAppointmentsByDoctorName("smith");

        assertNotNull(appointments);
        assertEquals(1, appointments.size());
        assertEquals("Dr. Smith", appointments.get(0).getDoctor().getName());
        verify(appointmentRepo, times(1)).findByDoctorNameContainingIgnoreCase("smith");
    }

    @Test
    void testFindAppointmentById_Found() {
        when(appointmentRepo.findById(1L)).thenReturn(Optional.of(appointment));

        Appointment foundAppointment = appointmentService.findAppointmentById(1L);

        assertNotNull(foundAppointment);
        assertEquals("John Doe", foundAppointment.getPatientName());
        assertEquals("john.doe@example.com", foundAppointment.getPatientEmail());
        verify(appointmentRepo, times(1)).findById(1L);
    }

    @Test
    void testFindAppointmentById_NotFound() {
        when(appointmentRepo.findById(1L)).thenReturn(Optional.empty());

        AppointmentNotFoundException exception = assertThrows(AppointmentNotFoundException.class, () ->
                appointmentService.findAppointmentById(1L));
        assertEquals("Appointment not found with ID: 1", exception.getMessage());
        verify(appointmentRepo, times(1)).findById(1L);
    }
}
