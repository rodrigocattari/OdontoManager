package com.gmc.odontomanager.mapper;

import com.gmc.odontomanager.entity.Employee;
import com.gmc.odontomanager.entity.dtos.EmployeeRequestDTO;
import com.gmc.odontomanager.entity.dtos.EmployeeResponseDTO;
import com.gmc.odontomanager.entity.dtos.EmployeeSummaryDTO;
import com.gmc.odontomanager.entity.enums.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Component
public class EmployeeMapper {

    private final PasswordEncoder passwordEncoder;

    public EmployeeMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Employee toEntity(EmployeeRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Set<Role> defaultRoles = new HashSet<>();
        defaultRoles.add(Role.DENTIST);

        return Employee.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .cpf(dto.getCpf())
                .birthDate(dto.getBirthDate())
                .password(encodePassword(dto.getPassword()))
                .cro(dto.getCro())
                .specialty(dto.getSpecialty())
                .roles(defaultRoles)
                .build();
    }

    public EmployeeResponseDTO toResponseDTO(Employee entity) {
        if (entity == null) {
            return null;
        }

        return EmployeeResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .cpf(entity.getCpf())
                .birthDate(entity.getBirthDate())
                .cro(entity.getCro())
                .specialty(entity.getSpecialty())
                .roles(entity.getRoles())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public EmployeeSummaryDTO toSummaryDTO(Employee entity) {
        if (entity == null) {
            return null;
        }

        return EmployeeSummaryDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cro(entity.getCro())
                .specialty(entity.getSpecialty())
                .phone(entity.getPhone())
                .build();
    }

    public void updateEntityFromDTO(EmployeeRequestDTO dto, Employee entity) {
        if (dto == null || entity == null) {
            return;
        }

        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if (dto.getPhone() != null) entity.setPhone(dto.getPhone());
        if (dto.getCpf() != null) entity.setCpf(dto.getCpf());
        if (dto.getBirthDate() != null) entity.setBirthDate(dto.getBirthDate());
        if (StringUtils.hasText(dto.getPassword())) entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        if (dto.getCro() != null) entity.setCro(dto.getCro());
        if (dto.getSpecialty() != null) entity.setSpecialty(dto.getSpecialty());
    }

    private String encodePassword(String rawPassword) {
        return StringUtils.hasText(rawPassword) ? passwordEncoder.encode(rawPassword) : null;
    }
}
