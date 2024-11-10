package com.Task.MiniProject_2.controller;

import com.Task.MiniProject_2.service.DoctorService;
import com.Task.MiniProject_2.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_PATIENT')")
    @GetMapping("/viewDoctors")
    public String viewDoctors(Model model) {
        List<Doctor> doctors = doctorService.displayAll();
        model.addAttribute("doctors", doctors);
        return "viewDoctors"; // This is the Thymeleaf template to display doctors
    }
}
