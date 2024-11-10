package com.Task.MiniProject_2.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;   // Appointment ID
    private String patientName;   // Patient's name
    private Integer patientAge;   // Patient's age
    private String patientGender; // Patient's gender
    private String patientEmail;  // Patient's email
    private String patientPhone;  // Patient's phone number
    private LocalDate appointmentDate; // Appointment date
    private String medicalReason; // Medical reason for the appointment

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;        // Doctor associated with the appointment


}
