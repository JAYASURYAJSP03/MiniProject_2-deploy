package com.Task.MiniProject_2.service;

import com.Task.MiniProject_2.entity.Medication;
import com.Task.MiniProject_2.entity.Appointment;
import com.Task.MiniProject_2.exception.AppointmentNotFoundException;
import com.Task.MiniProject_2.repository.MedicationRepository;
import com.Task.MiniProject_2.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    // Find appointment by ID
    public Appointment findAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId).orElse(null);
    }

    // Display all medications for an appointment
    public List<Medication> displayAll(Long appointmentId) {
        return medicationRepository.findByAppointmentId(appointmentId);
    }


    // Save medication to the database
    public void saveMedication(Long appointmentId, Medication medication) {
        Appointment appointment = findAppointmentById(appointmentId);
        if (appointment != null) {
            medication.setAppointment(appointment); // Set the appointment for the medication
            medicationRepository.save(medication);  // Save the medication
        }
    }


    // Get a medication by its ID
    public Medication getMedicationById(Long medicationId) {
        return medicationRepository.findById(medicationId).orElse(null);
    }

    // Update medication
    public void updateMedication(Long medicationId, Medication medication) {
        Medication existingMedication = getMedicationById(medicationId);
        if (existingMedication != null) {
            existingMedication.setName(medication.getName());
            existingMedication.setDosage(medication.getDosage());
            existingMedication.setFoodRelation(medication.getFoodRelation());
            existingMedication.setTiming(medication.getTiming());
            medicationRepository.save(existingMedication);  // Save the updated medication
        }
    }


    // Delete medication by ID
    public void deleteMedication(Long medicationId) {
        medicationRepository.deleteById(medicationId);  // Delete medication by ID
    }

}
