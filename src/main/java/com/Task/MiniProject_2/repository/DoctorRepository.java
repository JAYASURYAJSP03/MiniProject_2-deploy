


package com.Task.MiniProject_2.repository;

import com.Task.MiniProject_2.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // Additional custom queries for Doctor can be added here if needed
}
