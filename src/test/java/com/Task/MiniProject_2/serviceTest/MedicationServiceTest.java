package com.Task.MiniProject_2.serviceTest;

import com.Task.MiniProject_2.entity.Medication;
import com.Task.MiniProject_2.entity.Appointment;
import com.Task.MiniProject_2.service.MedicationService;
import com.Task.MiniProject_2.repository.MedicationRepository;
import com.Task.MiniProject_2.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicationServiceTest {

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private MedicationService medicationService;

    @Test
    public void testFindAppointmentById_ExistingId() {
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(appointmentId);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        Appointment result = medicationService.findAppointmentById(appointmentId);

        assertNotNull(result);
        assertEquals(appointmentId, result.getAppointmentId());
    }

    @Test
    public void testFindAppointmentById_NonExistingId() {
        Long appointmentId = 1L;

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        Appointment result = medicationService.findAppointmentById(appointmentId);

        assertNull(result);
    }

    @Test
    public void testDisplayAll_MedicationsExist() {
        Long appointmentId = 1L;
        List<Medication> medications = new ArrayList<>();
        medications.add(new Medication());

        when(medicationRepository.findByAppointmentId(appointmentId)).thenReturn(medications);

        List<Medication> result = medicationService.displayAll(appointmentId);

        assertEquals(1, result.size());
    }

    @Test
    public void testSaveMedication_ValidAppointment() {
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(appointmentId);

        Medication medication = new Medication();
        medication.setName("Test Medication");

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        medicationService.saveMedication(appointmentId, medication);

        verify(medicationRepository, times(1)).save(medication);
        assertEquals(appointment, medication.getAppointment());
    }

    @Test
    public void testGetMedicationById_ExistingId() {
        Long medicationId = 1L;
        Medication medication = new Medication();
        medication.setId(medicationId);

        when(medicationRepository.findById(medicationId)).thenReturn(Optional.of(medication));

        Medication result = medicationService.getMedicationById(medicationId);

        assertNotNull(result);
        assertEquals(medicationId, result.getId());
    }

    @Test
    public void testUpdateMedication_ExistingMedication() {
        Long medicationId = 1L;
        Medication existingMedication = new Medication();
        existingMedication.setId(medicationId);

        Medication updatedMedication = new Medication();
        updatedMedication.setName("Updated Medication");
        updatedMedication.setDosage("500mg");

        when(medicationRepository.findById(medicationId)).thenReturn(Optional.of(existingMedication));

        medicationService.updateMedication(medicationId, updatedMedication);

        verify(medicationRepository, times(1)).save(existingMedication);
        assertEquals("Updated Medication", existingMedication.getName());
        assertEquals("500mg", existingMedication.getDosage());
    }

    @Test
    public void testDeleteMedication_ExistingId() {
        Long medicationId = 1L;

        medicationService.deleteMedication(medicationId);

        verify(medicationRepository, times(1)).deleteById(medicationId);
    }
}
