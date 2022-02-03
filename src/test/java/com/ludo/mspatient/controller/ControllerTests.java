package com.ludo.mspatient.controller;

import com.ludo.mspatient.model.Patient;
import com.ludo.mspatient.service.PatientService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class ControllerTests {

    @MockBean
    PatientService patientService;

    @Autowired
    MockMvc mockMvc;


    @Test
    public void testFindAll() throws Exception {
        Patient patient = new Patient(1L, "test1", "test1", LocalDate.now(), "M", "1 rue toto", "0600000000");
        List<Patient> patients = Collections.singletonList(patient);

        when(patientService.getAllPatients()).thenReturn(patients);

        mockMvc.perform(get("/patient/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", Matchers.is("test1")));
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/patient/")).andExpect(status().isOk());
    }

    @Test
    public void testGetPatientById() throws Exception {
        Patient patient = new Patient(1L, "test1", "test1", LocalDate.now(), "M", "1 rue toto", "0600000000");
        when(patientService.getPatientById(1L)).thenReturn(patient);
        mockMvc.perform(get("/patient/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void TestUpdatePatient() throws Exception {

        Patient patient1 = new Patient(1L, "Doe1", "John1", LocalDate.of(2000, 10, 10), "M", "1 address", "100-200-4000");
        when(patientService.updatePatient(any(Long.class),any(Patient.class))).thenReturn(patient1);

        mockMvc.perform(post("/patient/update/1")
                .content("{ \"firstName\":\"Doe1\", \"lastName\":\"John1\", \"dob\":\"2020-10-10\", \"genre\":\"F\", \"address\":\"1 address\", \"phone\":\"100-222-1000\" }")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
