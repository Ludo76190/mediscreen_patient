package com.ludo.mspatient.service;

import com.ludo.mspatient.Exception.PatientNotFoundException;
import com.ludo.mspatient.model.Patient;
import com.ludo.mspatient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Long id, Patient patientToUpdate) throws PatientNotFoundException {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient", "id", id));

        patient.setFirstName(patientToUpdate.getFirstName());
        patient.setLastName((patientToUpdate.getLastName()));
        patient.setBirthdate(patientToUpdate.getBirthdate());
        patient.setSex(patientToUpdate.getSex());
        patient.setAddress(patientToUpdate.getAddress());
        patient.setPhone(patientToUpdate.getPhone());

        return patientRepository.save(patient);
    }

    @Override
    public void deletePatient(long id) throws PatientNotFoundException {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient", "id", id));;

        patientRepository.delete(patient);

    }

    @Override
    public Patient getPatientById(long id) throws PatientNotFoundException {

        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient", "id", id));
    }
}
