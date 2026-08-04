package com.gmc.odontomanager.service;

import com.gmc.odontomanager.entity.Anamnesis;
import com.gmc.odontomanager.entity.Disease;
import com.gmc.odontomanager.entity.Employee;
import com.gmc.odontomanager.entity.Patient;
import com.gmc.odontomanager.entity.dtos.AnamnesisRequestDTO;
import com.gmc.odontomanager.entity.dtos.AnamnesisResponseDTO;
import com.gmc.odontomanager.exeption.ResourceNotFoundException;
import com.gmc.odontomanager.mapper.AnamnesisMapper;
import com.gmc.odontomanager.repository.AnamnesisRepository;
import com.gmc.odontomanager.repository.DiseaseRepository;
import com.gmc.odontomanager.repository.EmployeeRepository;
import com.gmc.odontomanager.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class AnamnesisService {

    private final AnamnesisRepository anamnesisRepository;
    private final PatientRepository patientRepository;
    private final EmployeeRepository employeeRepository;
    private final DiseaseRepository diseaseRepository;
    private final AnamnesisMapper anamnesisMapper;

    public AnamnesisService(
            AnamnesisRepository anamnesisRepository,
            PatientRepository patientRepository,
            EmployeeRepository employeeRepository,
            DiseaseRepository diseaseRepository,
            AnamnesisMapper anamnesisMapper
    ) {
        this.anamnesisRepository = anamnesisRepository;
        this.patientRepository = patientRepository;
        this.employeeRepository = employeeRepository;
        this.diseaseRepository = diseaseRepository;
        this.anamnesisMapper = anamnesisMapper;
    }

    @Transactional(readOnly = true)
    public Page<AnamnesisResponseDTO> findAll(Pageable pageable) {
        return anamnesisRepository.findAll(pageable)
                .map(anamnesisMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public AnamnesisResponseDTO findById(Long id) {
        return anamnesisRepository.findById(id)
                .map(anamnesisMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Anamnesis not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public Page<AnamnesisResponseDTO> findByPatientId(Long patientId, Pageable pageable) {
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient not found with ID: " + patientId);
        }
        return anamnesisRepository.findByPatientId(patientId, pageable)
                .map(anamnesisMapper::toResponseDTO);
    }

    @Transactional
    public AnamnesisResponseDTO create(AnamnesisRequestDTO requestDto) {
        Patient patient = patientRepository.findById(requestDto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + requestDto.getPatientId()));

        Employee doctor = null;
        if (requestDto.getDoctorId() != null) {
            doctor = employeeRepository.findById(requestDto.getDoctorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor/Employee not found with ID: " + requestDto.getDoctorId()));
        }

        Set<Disease> diseases = new HashSet<>();
        if (requestDto.getDiseaseIds() != null && !requestDto.getDiseaseIds().isEmpty()) {
            diseases = new HashSet<>(diseaseRepository.findAllByIdIn(requestDto.getDiseaseIds()));
        }

        Anamnesis anamnesis = anamnesisMapper.toEntity(requestDto, patient, doctor, diseases);
        anamnesis = anamnesisRepository.save(anamnesis);
        return anamnesisMapper.toResponseDTO(anamnesis);
    }

    @Transactional
    public AnamnesisResponseDTO update(Long id, AnamnesisRequestDTO requestDto) {
        Anamnesis anamnesis = anamnesisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anamnesis not found with ID: " + id));

        Patient patient = patientRepository.findById(requestDto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + requestDto.getPatientId()));

        Employee doctor = null;
        if (requestDto.getDoctorId() != null) {
            doctor = employeeRepository.findById(requestDto.getDoctorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor/Employee not found with ID: " + requestDto.getDoctorId()));
        }

        Set<Disease> diseases = new HashSet<>();
        if (requestDto.getDiseaseIds() != null && !requestDto.getDiseaseIds().isEmpty()) {
            diseases = new HashSet<>(diseaseRepository.findAllByIdIn(requestDto.getDiseaseIds()));
        }

        anamnesisMapper.updateEntityFromDTO(requestDto, anamnesis, patient, doctor, diseases);
        anamnesis = anamnesisRepository.save(anamnesis);
        return anamnesisMapper.toResponseDTO(anamnesis);
    }

    @Transactional
    public void delete(Long id) {
        Anamnesis anamnesis = anamnesisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anamnesis not found with ID: " + id));

        anamnesisRepository.delete(anamnesis);
    }
}
