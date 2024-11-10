package com.Task.MiniProject_2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // Medicine ID
    private String name;         // Name of the medication
    private String dosage;       // Dosage information, e.g., "500mg"
    private String foodRelation; // "After food" or "Before food"
    private String timing;       // Timing, e.g., "Morning, Afternoon, Evening, Night"

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment; // The appointment this medication is associated with
}
