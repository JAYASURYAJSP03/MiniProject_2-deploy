package com.Task.MiniProject_2.serviceTest;

import com.Task.MiniProject_2.entity.Doctor;
import com.Task.MiniProject_2.repository.DoctorRepository;
import com.Task.MiniProject_2.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepo;

    @InjectMocks
    private DoctorService doctorService;

    @BeforeEach
    void setUp() {
        // Initialize any additional setup if needed
    }

    @Test
    void testDisplayAll() {
        // Arrange
        Doctor doctor1 = new Doctor(1L, "Dr. John", "Cardiology", 500.0, "10:00 AM - 12:00 PM", "Monday to Friday");
        Doctor doctor2 = new Doctor(2L, "Dr. Sarah", "Neurology", 700.0, "1:00 PM - 3:00 PM", "Monday to Friday");
        when(doctorRepo.findAll()).thenReturn(Arrays.asList(doctor1, doctor2));

        // Act
        List<Doctor> doctors = doctorService.displayAll();

        // Assert
        assertNotNull(doctors);
        assertEquals(2, doctors.size());
        assertEquals("Dr. John", doctors.get(0).getName());
        assertEquals("Cardiology", doctors.get(0).getSpecialization());
        assertEquals(500.0, doctors.get(0).getConsultationFee());
        assertEquals("Monday to Friday", doctors.get(0).getAvailableTime());
        verify(doctorRepo, times(1)).findAll();
    }

    @Test
    void testFindDoctorById_Found() {
        // Arrange
        Doctor doctor = new Doctor(1L, "Dr. John", "Cardiology", 500.0, "10:00 AM - 12:00 PM", "Monday to Friday");
        when(doctorRepo.findById(1L)).thenReturn(Optional.of(doctor));

        // Act
        Doctor foundDoctor = doctorService.findDoctorById(1L);

        // Assert
        assertNotNull(foundDoctor);
        assertEquals("Dr. John", foundDoctor.getName());
        assertEquals("Cardiology", foundDoctor.getSpecialization());
        assertEquals(500.0, foundDoctor.getConsultationFee());
        assertEquals("Monday to Friday", foundDoctor.getAvailableTime());
        verify(doctorRepo, times(1)).findById(1L);
    }

    @Test
    void testFindDoctorById_NotFound() {
        // Arrange
        when(doctorRepo.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> doctorService.findDoctorById(1L));
        assertEquals("Doctor not found with id: 1", exception.getMessage());
        verify(doctorRepo, times(1)).findById(1L);
    }
}
