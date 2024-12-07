package com.Task.MiniProject_2.controllerTest;

import com.Task.MiniProject_2.entity.Appointment;
import com.Task.MiniProject_2.entity.Doctor;
import com.Task.MiniProject_2.service.AppointmentService;
import com.Task.MiniProject_2.service.DoctorService;
import com.Task.MiniProject_2.controller.AppointmentController;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private DoctorService doctorService;

    @Mock
    private Model model;

    @InjectMocks
    private AppointmentController appointmentController;

    @Test
    public void testViewAppointments_AllAppointments() {
        List<Appointment> appointments = Collections.singletonList(new Appointment());
        when(appointmentService.displayAll()).thenReturn(appointments);

        String viewName = appointmentController.viewAppointments(null, null, model);

        verify(model).addAttribute("appointments", appointments);
        assertEquals("viewAppointments", viewName);
    }

    @Test
    public void testViewAppointments_ByPatientName() {
        String patientName = "John Doe";
        List<Appointment> appointments = Collections.singletonList(new Appointment());
        when(appointmentService.searchAppointmentsByPatientName(patientName)).thenReturn(appointments);

        String viewName = appointmentController.viewAppointments(patientName, null, model);

        verify(model).addAttribute("searchedAppointments", appointments);
        assertEquals("viewAppointments", viewName);
    }

    @Test
    public void testViewAppointments_ByDoctorName() {
        String doctorName = "Dr. Smith";
        List<Appointment> appointments = Collections.singletonList(new Appointment());
        when(appointmentService.searchAppointmentsByDoctorName(doctorName)).thenReturn(appointments);

        String viewName = appointmentController.viewAppointments(null, doctorName, model);

        verify(model).addAttribute("searchedAppointments", appointments);
        assertEquals("viewAppointments", viewName);
    }

    @Test
    public void testBookAppointment() {
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        doctor.setDoctorId(doctorId);
        when(doctorService.findDoctorById(doctorId)).thenReturn(doctor);

        String viewName = appointmentController.bookAppointment(doctorId, model);

        verify(model).addAttribute(eq("appointment"), any(Appointment.class));
        assertEquals("bookAppointment", viewName);
    }

    @Test
    public void testAddAppointment() {
        Appointment appointment = new Appointment();
        Doctor doctor = new Doctor();
        doctor.setDoctorId(1L);
        appointment.setDoctor(doctor);

        String viewName = appointmentController.addAppointment(appointment);

        verify(appointmentService).addAppointment(appointment);
        assertEquals("redirect:/appointmentSuccess", viewName);
    }

    @Test
    public void testAppointmentSuccess() {
        String viewName = appointmentController.appointmentSuccess();
        assertEquals("appointmentSuccess", viewName);
    }
}
