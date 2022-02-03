package com.ludo.mspatient.integration;

import com.ludo.mspatient.controller.PatientController;
import com.ludo.mspatient.dto.PatientDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class IntegrationTests {

    @Autowired
    PatientController patientController;

    @Test
    public void testCreateReadDelete() {
        PatientDto patient = new PatientDto(1L, "test10", "test10", LocalDate.now(), "M", "1 rue toto", "0600000000");

        ResponseEntity<PatientDto> patientResult = patientController.createPatient(patient);

        Iterable<PatientDto> patients = patientController.getAllPatients();
        Assertions.assertThat(patients).first().hasFieldOrPropertyWithValue("firstName", "test10");

        patientController.deletePatient(patient.getId());
        Assertions.assertThat(patientController.getAllPatients()).isEmpty();
    }

}
