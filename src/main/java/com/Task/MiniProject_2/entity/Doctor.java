package com.Task.MiniProject_2.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;           // Unique ID for each doctor
    private String name;             // Doctor's name
    private String specialization;   // Doctor's specialization, e.g., "Cardiologist"
    private Double consultationFee;  // Consultation fee
    private String availableSlot;    // Available slots, e.g., "10:00 AM - 12:00 PM"
    private String availableTime;    // General available time, e.g., "Monday to Friday"


}
