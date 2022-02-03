package com.ludo.mspatient.service;

import com.ludo.mspatient.Exception.PatientNotFoundException;
import com.ludo.mspatient.model.Patient;

import java.util.List;

public interface PatientService {

    List<Patient> getAllPatients();
    Patient createPatient(Patient patient);
    Patient updatePatient(Long id, Patient patient) throws PatientNotFoundException;
    void deletePatient(long id) throws PatientNotFoundException;
    Patient getPatientById(long id) throws PatientNotFoundException;

}
