package com.gmc.odontomanager.mapper;

import com.gmc.odontomanager.entity.Patient;
import com.gmc.odontomanager.entity.dtos.PatientRequestDTO;
import com.gmc.odontomanager.entity.dtos.PatientResponseDTO;
import com.gmc.odontomanager.entity.dtos.PatientSummaryDTO;
import com.gmc.odontomanager.entity.enums.Role;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PatientMapper {

    public Patient toEntity(PatientRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Set<Role> defaultRoles = new HashSet<>();
        defaultRoles.add(Role.PATIENT);

        return Patient.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .cpf(dto.getCpf())
                .birthDate(dto.getBirthDate())
                .password(dto.getPassword() != null && !dto.getPassword().isBlank() ? dto.getPassword() : "123456")
                .address(dto.getAddress())
                .roles(defaultRoles)
                .build();
    }

    public PatientResponseDTO toResponseDTO(Patient entity) {
        if (entity == null) {
            return null;
        }

        return PatientResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .cpf(entity.getCpf())
                .birthDate(entity.getBirthDate())
                .address(entity.getAddress())
                .roles(entity.getRoles())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public PatientSummaryDTO toSummaryDTO(Patient entity) {
        if (entity == null) {
            return null;
        }

        return PatientSummaryDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cpf(entity.getCpf())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .build();
    }

    public void updateEntityFromDTO(PatientRequestDTO dto, Patient entity) {
        if (dto == null || entity == null) {
            return;
        }

        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if (dto.getPhone() != null) entity.setPhone(dto.getPhone());
        if (dto.getCpf() != null) entity.setCpf(dto.getCpf());
        if (dto.getBirthDate() != null) entity.setBirthDate(dto.getBirthDate());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) entity.setPassword(dto.getPassword());
        if (dto.getAddress() != null) entity.setAddress(dto.getAddress());
    }
}
