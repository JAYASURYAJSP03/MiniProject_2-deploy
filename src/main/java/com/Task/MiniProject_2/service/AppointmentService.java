
package com.Task.MiniProject_2.service;

import com.Task.MiniProject_2.entity.Appointment;
import com.Task.MiniProject_2.entity.Doctor;
import com.Task.MiniProject_2.repository.AppointmentRepository;
import com.Task.MiniProject_2.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Task.MiniProject_2.exception.AppointmentNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Appointment> displayAll(){
        return appointmentRepo.findAll();
    }

    public void addAppointment(Appointment appointment){
        Doctor doctor=doctorRepository.findById(appointment.getDoctor().getDoctorId()).get();
//        doctor.setName(doctor.getName().trim());
        appointment.setDoctor(doctor);
        appointment.setPatientName(appointment.getPatientName().trim());
        appointmentRepo.save(appointment);
    }
    // Search appointments by patient's name (case-insensitive)
    public List<Appointment> searchAppointmentsByPatientName(String patientName) {
        return appointmentRepo.findByPatientNameIgnoreCase(patientName.trim());
    }

    public List<Appointment> searchAppointmentsByDoctorName(String doctorName) {
        return appointmentRepo.findByDoctorNameContainingIgnoreCase(doctorName.trim());
    }


    // Find appointment by ID with exception handling
    public Appointment findAppointmentById(Long appointmentId) {
        Optional<Appointment> optionalAppointment = appointmentRepo.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
            return optionalAppointment.get();
        } else {
            throw new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId);
        }
    }
}

