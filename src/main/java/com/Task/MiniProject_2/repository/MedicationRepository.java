package com.Task.MiniProject_2.repository;

import com.Task.MiniProject_2.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    // Correct query to fetch medications based on the associated appointment
    @Query("SELECT m FROM Medication m WHERE m.appointment.appointmentId = :appointmentId")
    List<Medication> findByAppointmentId(Long appointmentId);

}
