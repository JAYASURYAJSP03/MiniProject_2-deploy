package com.Task.MiniProject_2.controller;

import com.Task.MiniProject_2.service.MedicationService;
import com.Task.MiniProject_2.entity.Medication;
import com.Task.MiniProject_2.entity.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    // Display medications for a given appointment ID
    @PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_PATIENT')")
    @GetMapping("/viewMedications")
    public String viewMedications() {
        return "viewMedications"; // Return the Thymeleaf page to enter appointment ID and view medications
    }

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_PATIENT')")
    @GetMapping("/showMedications")
    public String showMedications(@RequestParam Long appointmentId, Model model) {
        // Find appointment by ID and display medications if appointment exists
        Appointment appointment = medicationService.findAppointmentById(appointmentId);
        if (appointment == null) {
            model.addAttribute("errorMessage", "Appointment not found!  " + appointmentId);
            return "viewMedications";
        }
        model.addAttribute("appointment", appointment);
        return "viewMedications"; // Show medication details for the appointment ID
    }


    @PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_PATIENT')")
    @GetMapping("/medications/{appointmentId}")
    public String viewMedicationsPage(@PathVariable Long appointmentId, Model model) {
        Appointment appointment = medicationService.findAppointmentById(appointmentId);

        // Get the list of medications for the appointment
        List<Medication> medications = medicationService.displayAll(appointmentId);

        model.addAttribute("appointment", appointment);
        model.addAttribute("medications", medications);  // Add medications to model
        model.addAttribute("appointmentId", appointmentId); // Add appointmentId separately
        return "medications";
    }

    // Add medication
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/addMedication/{appointmentId}")
    public String addMedication(@PathVariable Long appointmentId, Model model) {
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("medication", new Medication());
        return "addMedication"; // Show form for adding medication
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PostMapping("/saveMedication/{appointmentId}")
    public String saveMedication(@PathVariable Long appointmentId, @ModelAttribute Medication medication) {
        medicationService.saveMedication(appointmentId, medication);
        return "redirect:/medications/" + appointmentId; // Redirect back to medications page after saving
    }

    // Edit medication details
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/editMedication/{appointmentId}/{medicationId}")
    public String editMedication(@PathVariable Long appointmentId, @PathVariable Long medicationId, Model model) {
        Medication medication = medicationService.getMedicationById(medicationId);
        if (medication != null) {
            model.addAttribute("medication", medication);
            model.addAttribute("appointmentId", appointmentId);
            return "editMedication"; // Show form for editing medication
        } else {
            return "redirect:/medications/" + appointmentId; // Medication not found, redirect
        }
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PostMapping("/updateMedication/{appointmentId}/{medicationId}")
    public String updateMedication(@PathVariable Long appointmentId, @PathVariable Long medicationId, @ModelAttribute Medication medication) {
        medicationService.updateMedication(medicationId, medication);
        return "redirect:/medications/" + appointmentId; // Redirect to medications page after update
    }

    // Delete medication
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/deleteMedication/{appointmentId}/{medicationId}")
    public String deleteMedication(@PathVariable Long appointmentId, @PathVariable Long medicationId) {
        medicationService.deleteMedication(medicationId);
        return "redirect:/medications/" + appointmentId; // Redirect back to medications page after deletion
    }


}
