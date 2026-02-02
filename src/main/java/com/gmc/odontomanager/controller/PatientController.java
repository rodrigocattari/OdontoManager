package com.gmc.odontomanager.controller;

import com.gmc.odontomanager.controller.swagger.PatientControllerSwagger;
import com.gmc.odontomanager.entity.Patient;
import com.gmc.odontomanager.entity.dtos.PatientDTO;
import com.gmc.odontomanager.repository.PatientRepository;
import com.gmc.odontomanager.service.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient")
public class PatientController implements PatientControllerSwagger {

    private final PatientService patientService;

    public PatientController(PatientRepository patientRepository, PatientService patientService, ModelMapper modelMapper) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<Page<Patient>> findAllPatient(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return patientService.findAllPatient(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> findPatientById(@PathVariable Long id) {
        return patientService.findPatientById(id);
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.createPatient(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDto) {
        return patientService.updatePatient(id, patientDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
                    return patientService.deletePatient(id);
    }
}