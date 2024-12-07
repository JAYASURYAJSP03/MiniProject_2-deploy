package com.Task.MiniProject_2.controllerTest;


import com.Task.MiniProject_2.entity.Doctor;
import com.Task.MiniProject_2.service.DoctorService;
import com.Task.MiniProject_2.controller.DoctorController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @Mock
    private Model model;

    @InjectMocks
    private DoctorController doctorController;

    @Test
    public void testViewDoctors() {
        // Arrange: Set up a sample list of doctors
        List<Doctor> doctors = Collections.singletonList(new Doctor());
        when(doctorService.displayAll()).thenReturn(doctors);

        // Act: Call the method under test
        String viewName = doctorController.viewDoctors(model);

        // Assert: Verify interactions and returned view name
        verify(doctorService, times(1)).displayAll();
        verify(model, times(1)).addAttribute("doctors", doctors);
        assertEquals("viewDoctors", viewName);
    }
}
