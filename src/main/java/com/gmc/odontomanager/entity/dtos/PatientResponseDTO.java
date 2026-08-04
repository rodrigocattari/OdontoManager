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
@Schema(description = "Detailed response for Patient entity")
public class PatientResponseDTO {

    @Schema(description = "Unique identifier of the patient", example = "1")
    private Long id;

    @Schema(description = "Full name of the patient", example = "Maria Silva Santos")
    private String name;

    @Schema(description = "Email address", example = "maria.silva@email.com")
    private String email;

    @Schema(description = "Contact phone number", example = "+55 11 98765-4321")
    private String phone;

    @Schema(description = "Brazilian Individual Taxpayer Registry (CPF)", example = "123.456.789-00")
    private String cpf;

    @Schema(description = "Date of birth", example = "1990-05-15")
    private LocalDate birthDate;

    @Schema(description = "Residential address", example = "Av. Paulista, 1000, Apt 52, São Paulo - SP")
    private String address;

    @Schema(description = "Assigned user roles", example = "[\"PATIENT\"]")
    private Set<Role> roles;

    @Schema(description = "Record creation timestamp", example = "2026-08-03T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Record last modification timestamp", example = "2026-08-03T10:30:00")
    private LocalDateTime updatedAt;
}
