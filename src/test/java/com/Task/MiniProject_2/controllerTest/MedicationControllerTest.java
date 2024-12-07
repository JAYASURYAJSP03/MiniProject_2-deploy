package com.Task.MiniProject_2.controllerTest;

import com.Task.MiniProject_2.controller.MedicationController;
import com.Task.MiniProject_2.entity.Appointment;
import com.Task.MiniProject_2.entity.Medication;
import com.Task.MiniProject_2.service.MedicationService;
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
public class MedicationControllerTest {

    @Mock
    private MedicationService medicationService;

    @Mock
    private Model model;

    @InjectMocks
    private MedicationController medicationController;

    @Test
    public void testViewMedications() {
        String viewName = medicationController.viewMedications();
        assertEquals("viewMedications", viewName);
    }

    @Test
    public void testShowMedications_AppointmentFound() {
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        when(medicationService.findAppointmentById(appointmentId)).thenReturn(appointment);

        String viewName = medicationController.showMedications(appointmentId, model);

        verify(model).addAttribute("appointment", appointment);
        assertEquals("viewMedications", viewName);
    }

    @Test
    public void testShowMedications_AppointmentNotFound() {
        Long appointmentId = 1L;
        when(medicationService.findAppointmentById(appointmentId)).thenReturn(null);

        String viewName = medicationController.showMedications(appointmentId, model);

        verify(model).addAttribute("errorMessage", "Appointment not found!  " + appointmentId);
        assertEquals("viewMedications", viewName);
    }

    @Test
    public void testViewMedicationsPage() {
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        List<Medication> medications = Collections.singletonList(new Medication());

        when(medicationService.findAppointmentById(appointmentId)).thenReturn(appointment);
        when(medicationService.displayAll(appointmentId)).thenReturn(medications);

        String viewName = medicationController.viewMedicationsPage(appointmentId, model);

        verify(model).addAttribute("appointment", appointment);
        verify(model).addAttribute("medications", medications);
        verify(model).addAttribute("appointmentId", appointmentId);
        assertEquals("medications", viewName);
    }

    @Test
    public void testAddMedication() {
        Long appointmentId = 1L;

        String viewName = medicationController.addMedication(appointmentId, model);

        verify(model).addAttribute("appointmentId", appointmentId);
        verify(model).addAttribute("medication", new Medication());
        assertEquals("addMedication", viewName);
    }

    @Test
    public void testSaveMedication() {
        Long appointmentId = 1L;
        Medication medication = new Medication();

        String viewName = medicationController.saveMedication(appointmentId, medication);

        verify(medicationService).saveMedication(appointmentId, medication);
        assertEquals("redirect:/medications/" + appointmentId, viewName);
    }

    @Test
    public void testEditMedication_MedicationFound() {
        Long appointmentId = 1L;
        Long medicationId = 2L;
        Medication medication = new Medication();

        when(medicationService.getMedicationById(medicationId)).thenReturn(medication);

        String viewName = medicationController.editMedication(appointmentId, medicationId, model);

        verify(model).addAttribute("medication", medication);
        verify(model).addAttribute("appointmentId", appointmentId);
        assertEquals("editMedication", viewName);
    }

    @Test
    public void testEditMedication_MedicationNotFound() {
        Long appointmentId = 1L;
        Long medicationId = 2L;

        when(medicationService.getMedicationById(medicationId)).thenReturn(null);

        String viewName = medicationController.editMedication(appointmentId, medicationId, model);

        assertEquals("redirect:/medications/" + appointmentId, viewName);
    }

    @Test
    public void testUpdateMedication() {
        Long appointmentId = 1L;
        Long medicationId = 2L;
        Medication medication = new Medication();

        String viewName = medicationController.updateMedication(appointmentId, medicationId, medication);

        verify(medicationService).updateMedication(medicationId, medication);
        assertEquals("redirect:/medications/" + appointmentId, viewName);
    }

    @Test
    public void testDeleteMedication() {
        Long appointmentId = 1L;
        Long medicationId = 2L;

        String viewName = medicationController.deleteMedication(appointmentId, medicationId);

        verify(medicationService).deleteMedication(medicationId);
        assertEquals("redirect:/medications/" + appointmentId, viewName);
    }
}
