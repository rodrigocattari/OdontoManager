package com.gmc.odontomanager.entity.dtos;

import com.gmc.odontomanager.entity.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detailed response for Employee entity")
public class EmployeeResponseDTO {

    @Schema(description = "Unique identifier of the employee", example = "2")
    private Long id;

    @Schema(description = "Full name of the employee/doctor", example = "Dr. Carlos Eduardo Lima")
    private String name;

    @Schema(description = "Email address", example = "carlos.lima@odontomanager.com")
    private String email;

    @Schema(description = "Contact phone number", example = "+55 11 91234-5678")
    private String phone;

    @Schema(description = "Brazilian Individual Taxpayer Registry (CPF)", example = "987.654.321-00")
    private String cpf;

    @Schema(description = "Date of birth", example = "1985-08-20")
    private LocalDate birthDate;

    @Schema(description = "Regional Dental Council Registration (CRO)", example = "CRO-SP 123456")
    private String cro;

    @Schema(description = "Dental specialty", example = "Ortodontia")
    private String specialty;

    @Schema(description = "Assigned user roles", example = "[\"DENTIST\"]")
    private Set<Role> roles;

    @Schema(description = "Record creation timestamp", example = "2026-08-03T09:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Record last modification timestamp", example = "2026-08-03T09:15:00")
    private LocalDateTime updatedAt;
}
