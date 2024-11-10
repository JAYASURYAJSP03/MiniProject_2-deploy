package com.Task.MiniProject_2.controller;

import com.Task.MiniProject_2.service.DoctorService;
import com.Task.MiniProject_2.service.AppointmentService;
import com.Task.MiniProject_2.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private MedicationService medicationService;

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_PATIENT')")
    @GetMapping("/home")
    public String homePage() {
        return "home"; // Return the Thymeleaf template for home page
    }
}
