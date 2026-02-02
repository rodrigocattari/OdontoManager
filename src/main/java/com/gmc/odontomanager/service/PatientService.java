package com.gmc.odontomanager.service;

import com.gmc.odontomanager.entity.Patient;
import com.gmc.odontomanager.entity.dtos.PatientDTO;
import com.gmc.odontomanager.exeption.ResourceNotFoundException;
import com.gmc.odontomanager.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public ResponseEntity<Page<Patient>> findAllPatient(Pageable pageable) {
        Page<Patient> patients = patientRepository.findAll(pageable);
        return ResponseEntity.ok(patients);
    }

    public ResponseEntity<Patient> findPatientById(Long id) {
        return patientRepository.findById(id)
                .map(patient -> ResponseEntity.ok().body(patient))
                .orElse(ResponseEntity.notFound().build());
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public ResponseEntity<Patient> updatePatient(Long id, PatientDTO patientDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com id: " + id));

        if (null != patientDto.getName()) {patient.setName(patientDto.getName());}
        if (null != patientDto.getEmail()) {patient.setEmail(patientDto.getEmail());}
        if (null != patientDto.getPhone()) {patient.setPhone(patientDto.getPhone());}
        if (null != patientDto.getCpf()) {patient.setCpf(patientDto.getCpf());}
        if (null != patientDto.getDateOfBirth()) {patient.setDateOfBirth(patientDto.getDateOfBirth());}
        if (null != patientDto.getIsActive()) {patient.setIsActive(patientDto.getIsActive());}

        return ResponseEntity.ok(patientRepository.save(patient));
    }

    public ResponseEntity<Void> deletePatient(Long id) {
        return patientRepository.findById(id)
                .map(patient -> {
                    patientRepository.delete(patient);
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
