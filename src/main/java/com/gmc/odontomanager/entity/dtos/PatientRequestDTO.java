package com.gmc.odontomanager.entity.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Payload for creating or updating a Patient")
public class PatientRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    @Schema(description = "Full name of the patient", example = "Maria Silva Santos")
    private String name;

    @Email(message = "Email must be valid")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    @Schema(description = "Email address", example = "maria.silva@email.com")
    private String email;

    @Size(max = 50, message = "Phone must not exceed 50 characters")
    @Schema(description = "Contact phone number", example = "+55 11 98765-4321")
    private String phone;

    @CPF(message = "CPF must be valid")
    @Schema(description = "Brazilian Individual Taxpayer Registry (CPF)", example = "123.456.789-00")
    private String cpf;

    @Past(message = "Birth date must be in the past")
    @Schema(description = "Date of birth", example = "1990-05-15")
    private LocalDate birthDate;

    @Schema(description = "Access password", example = "Secret@123")
    private String password;

    @Size(max = 255, message = "Address must not exceed 255 characters")
    @Schema(description = "Residential address", example = "Av. Paulista, 1000, Apt 52, São Paulo - SP")
    private String address;
}
