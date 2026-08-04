package com.gmc.odontomanager.controller;

import com.gmc.odontomanager.controller.swagger.PatientControllerSwagger;
import com.gmc.odontomanager.entity.dtos.PatientRequestDTO;
import com.gmc.odontomanager.entity.dtos.PatientResponseDTO;
import com.gmc.odontomanager.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/patient")
public class PatientController implements PatientControllerSwagger {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<PatientResponseDTO>> findAllPatient(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<PatientResponseDTO> patients = patientService.findAll(pageable);
        return ResponseEntity.ok(patients);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> findPatientById(@PathVariable Long id) {
        PatientResponseDTO patient = patientService.findById(id);
        return ResponseEntity.ok(patient);
    }

    @Override
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO requestDto) {
        PatientResponseDTO createdPatient = patientService.create(requestDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPatient.getId())
                .toUri();
        return ResponseEntity.created(uri).body(createdPatient);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequestDTO requestDto
    ) {
        PatientResponseDTO updatedPatient = patientService.update(id, requestDto);
        return ResponseEntity.ok(updatedPatient);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}