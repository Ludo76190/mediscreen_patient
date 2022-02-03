package com.ludo.mspatient.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ludo.mspatient.dto.PatientDto;
import com.ludo.mspatient.model.Patient;
import com.ludo.mspatient.service.PatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api("API to manage patient informations")
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private ModelMapper modelMapper;

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        super();
        this.patientService = patientService;
    }

    @GetMapping("/")
    public String index() {
        return "Greeting to Patient Microservice";
    }

    @ApiOperation(value = "Get the list of patients")
    @GetMapping("/list")
    public List<PatientDto> getAllPatients() {
        return patientService.getAllPatients().stream().map(post -> modelMapper.map(post, PatientDto.class))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Get the patient with ID")
    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable(name = "id") Long id) {

        Patient patient = patientService.getPatientById(id);

        PatientDto patientResponse = modelMapper.map(patient, PatientDto.class);

        return ResponseEntity.ok().body(patientResponse);
    }

    @ApiOperation(value = "Add a patient")
    @PostMapping("/add")
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientDto patientDto) {

        Patient patientRequest = modelMapper.map(patientDto, Patient.class);

        Patient patient = patientService.createPatient(patientRequest);

        PatientDto patientResponse = modelMapper.map(patient, PatientDto.class);

        return new ResponseEntity<PatientDto>(patientResponse, HttpStatus.CREATED);

    }

    @ApiOperation(value = "Update a patient")
    @PostMapping("/update/{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable("id") long id, @RequestBody PatientDto patientDto) throws JsonProcessingException {

        Patient patientRequest = modelMapper.map(patientDto, Patient.class);

        Patient patient = patientService.updatePatient(id, patientRequest);

        PatientDto patientResponse = modelMapper.map(patient, PatientDto.class);

        return ResponseEntity.ok(patientResponse);
    }

    @ApiOperation(value = "Delete a patient")
    @GetMapping("/delete/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }
}
