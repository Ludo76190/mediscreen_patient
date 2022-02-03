package com.ludo.mspatient.repository;

import com.ludo.mspatient.model.Patient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTests {

    @Autowired
    PatientRepository patientRepository;

    @Test
    public void testCreateReadDelete() {
        Patient patient = new Patient(1L, "test1", "test1", LocalDate.now(), "M", "1 rue toto", "0600000000");

        patientRepository.save(patient);

        Iterable<Patient> patients = patientRepository.findAll();
        Assertions.assertThat(patients).extracting(Patient::getFirstName).containsOnly("test1");

        patientRepository.deleteAll();
        Assertions.assertThat(patientRepository.findAll()).isEmpty();
    }
}
