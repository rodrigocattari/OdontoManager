package com.gmc.odontomanager.service;

import com.gmc.odontomanager.entity.Patient;
import com.gmc.odontomanager.entity.dtos.PatientRequestDTO;
import com.gmc.odontomanager.entity.dtos.PatientResponseDTO;
import com.gmc.odontomanager.exeption.ResourceNotFoundException;
import com.gmc.odontomanager.mapper.PatientMapper;
import com.gmc.odontomanager.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    @Transactional(readOnly = true)
    public Page<PatientResponseDTO> findAll(Pageable pageable) {
        return patientRepository.findAll(pageable)
                .map(patientMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public PatientResponseDTO findById(Long id) {
        return patientRepository.findById(id)
                .map(patientMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));
    }

    @Transactional
    public PatientResponseDTO create(PatientRequestDTO requestDto) {
        if (requestDto.getCpf() != null && !requestDto.getCpf().isBlank() && patientRepository.existsByCpf(requestDto.getCpf())) {
            throw new IllegalArgumentException("A patient with CPF '" + requestDto.getCpf() + "' already exists.");
        }

        if (requestDto.getEmail() != null && !requestDto.getEmail().isBlank() && patientRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("A patient with email '" + requestDto.getEmail() + "' already exists.");
        }

        Patient patient = patientMapper.toEntity(requestDto);
        patient = patientRepository.save(patient);
        return patientMapper.toResponseDTO(patient);
    }

    @Transactional
    public PatientResponseDTO update(Long id, PatientRequestDTO requestDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));

        if (requestDto.getCpf() != null && !requestDto.getCpf().isBlank() && patientRepository.existsByCpfAndIdNot(requestDto.getCpf(), id)) {
            throw new IllegalArgumentException("Another patient with CPF '" + requestDto.getCpf() + "' already exists.");
        }

        if (requestDto.getEmail() != null && !requestDto.getEmail().isBlank() && patientRepository.existsByEmailAndIdNot(requestDto.getEmail(), id)) {
            throw new IllegalArgumentException("Another patient with email '" + requestDto.getEmail() + "' already exists.");
        }

        patientMapper.updateEntityFromDTO(requestDto, patient);
        patient = patientRepository.save(patient);
        return patientMapper.toResponseDTO(patient);
    }

    @Transactional
    public void delete(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));

        patientRepository.delete(patient);
    }
}
