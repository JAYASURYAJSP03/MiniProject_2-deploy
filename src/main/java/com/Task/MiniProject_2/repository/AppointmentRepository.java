package com.Task.MiniProject_2.repository;


import com.Task.MiniProject_2.entity.Appointment;
import com.Task.MiniProject_2.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientNameIgnoreCase(String patientName);

    List<Appointment> findByDoctorNameContainingIgnoreCase(String doctorName);

    List<Medication> findByAppointmentId(Long appointmentId);

}
