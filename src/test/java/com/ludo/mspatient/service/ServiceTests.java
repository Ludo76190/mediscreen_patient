package com.ludo.mspatient.service;

import com.ludo.mspatient.Exception.PatientNotFoundException;
import com.ludo.mspatient.model.Patient;
import com.ludo.mspatient.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ServiceTests {

    @InjectMocks
    PatientServiceImpl patientService;

    @Mock
    PatientRepository patientRepository;

    @Test
    public void testFindAllEmployees() {
        List<Patient> list = new ArrayList<>();
        Patient patientOne = new Patient(1L, "test1", "test1", LocalDate.now(), "M", "1 rue toto", "0600000000");
        Patient patientTwo = new Patient(2L, "test2", "test2", LocalDate.now(), "M", "1 rue toto", "0600000000");
        Patient patientThree = new Patient(3L, "test3", "test3", LocalDate.now(), "M", "1 rue toto", "0600000000");

        list.add(patientOne);
        list.add(patientTwo);
        list.add(patientThree);

        when(patientRepository.findAll()).thenReturn(list);

        List<Patient> empList = patientService.getAllPatients();

        assertEquals(3, empList.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    public void TestGetPatientByIdWhenPatientExists() {
        Patient patient = new Patient(1L, "test1", "test1", LocalDate.now(), "M", "1 rue toto", "0600000000");
        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        assertTrue(patientService.getPatientById(1).toString().equalsIgnoreCase(patient.toString()));
    }

    @Test
    public void TestGetPatientByIdWhenPatientDoesNotExists() {
        Mockito.when(patientRepository.findById(0L)).thenReturn(Optional.empty());
        PatientNotFoundException exception = assertThrows(PatientNotFoundException.class, () -> patientService.getPatientById(0));

        assertEquals("Patient not found with id : '0'", exception.getMessage());
    }


    @Test
    public void testCreatePatient() {

        Patient patient = new Patient(1L, "test1", "test1", LocalDate.now(), "M", "1 rue toto", "0600000000");
        patientService.createPatient(patient);
        verify(patientRepository, times(1)).save(patient);

    }

    @Test
    public void testUpdatePatientWhenPatientExists() {

        Patient patient = new Patient(1L, "test1", "test1", LocalDate.now(), "M", "1 rue toto", "0600000000");
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientRepository.save(patient)).thenReturn(patient);
        patientService.updatePatient(1L, patient);

        verify(patientRepository, times(1)).save(patient);

    }

    @Test
    public void testUpdatePatientWhenPatientDoesNotExists() {

        when(patientRepository.findById(0L)).thenReturn(Optional.empty());
        assertThrows(PatientNotFoundException.class, () -> patientService.updatePatient(0L, any(Patient.class)));
        verify(patientRepository, times(0)).save(any(Patient.class));

    }

    @Test
    void TestDeleteUserDoesNotExists() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(PatientNotFoundException.class, ()
                -> patientService.deletePatient(1L));

        assertThat(exception.getMessage()).isEqualTo("Patient not found with id : '1'");
        verify(patientRepository, times(1)).findById(1L);

    }
}