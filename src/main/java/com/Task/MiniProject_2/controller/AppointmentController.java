package com.Task.MiniProject_2.controller;

import com.Task.MiniProject_2.entity.Doctor;
import com.Task.MiniProject_2.entity.Medication;
import com.Task.MiniProject_2.service.AppointmentService;
import com.Task.MiniProject_2.entity.Appointment;
import com.Task.MiniProject_2.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private DoctorService doctorService;

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_PATIENT')")
    @GetMapping("/viewAppointments")
    public String viewAppointments(
            @RequestParam(value = "patientName", required = false) String patientName,
            @RequestParam(value = "doctorName", required = false) String doctorName,
            Model model) {
        // Declare a list to hold appointment data
        List<Appointment> appointments;

        // Check if either patient name or doctor name is provided for searching
        if (patientName != null && !patientName.isEmpty()) {
            // If a patient name is provided, perform a search for appointments by patient name
            appointments = appointmentService.searchAppointmentsByPatientName(patientName);
            model.addAttribute("searchedAppointments", appointments);
        } else if (doctorName != null && !doctorName.isEmpty()) {
            // If a doctor name is provided, perform a search for appointments by doctor name
            appointments = appointmentService.searchAppointmentsByDoctorName(doctorName);
            model.addAttribute("searchedAppointments", appointments);
        } else {
            // If no search parameters are provided, retrieve all appointments
            appointments = appointmentService.displayAll();
            model.addAttribute("appointments", appointments);
        }

        // Return the "viewAppointments" view to display the appointments (either all or search results)
        return "viewAppointments";
    }





    // Book an appointment (This will take the user to the appointment booking page)
    @PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_PATIENT')")
    @GetMapping("/bookAppointment/{doctorId}")
    public String bookAppointment(@PathVariable Long doctorId, Model model) {

        Appointment appointment = new Appointment();

        Doctor doctor = doctorService.findDoctorById(doctorId);
        appointment.setDoctor(doctor);
//        model.addAttribute("doctor", doctor);  // Adding the doctor to the model
        model.addAttribute("appointment", appointment); // appointment now contains medications

        return "bookAppointment"; // Thymeleaf template for booking an appointment
    }



    // Handle the booking form submission
    @PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_PATIENT')")
    @PostMapping("/addAppointment")
    public String addAppointment(@ModelAttribute Appointment appointment) {
        long doctorId = appointment.getDoctor().getDoctorId();

        // Save the appointment with the complete doctor information
        appointmentService.addAppointment(appointment);

        // Redirect to success page after appointment is added
        return "redirect:/appointmentSuccess";
    }

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_PATIENT')")
    @GetMapping("/appointmentSuccess")
    public String appointmentSuccess() {
        return "appointmentSuccess";
    }



}
