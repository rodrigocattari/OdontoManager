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
@Schema(description = "Payload for creating or updating an Employee / Doctor")
public class EmployeeRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    @Schema(description = "Full name of the employee/doctor", example = "Dr. Carlos Eduardo Lima")
    private String name;

    @Email(message = "Email must be valid")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    @Schema(description = "Email address", example = "carlos.lima@odontomanager.com")
    private String email;

    @Size(max = 50, message = "Phone must not exceed 50 characters")
    @Schema(description = "Contact phone number", example = "+55 11 91234-5678")
    private String phone;

    @CPF(message = "CPF must be valid")
    @Schema(description = "Brazilian Individual Taxpayer Registry (CPF)", example = "987.654.321-00")
    private String cpf;

    @Past(message = "Birth date must be in the past")
    @Schema(description = "Date of birth", example = "1985-08-20")
    private LocalDate birthDate;

    @Schema(description = "Access password", example = "Doctor@123")
    private String password;

    @Size(max = 50, message = "CRO must not exceed 50 characters")
    @Schema(description = "Regional Dental Council Registration (CRO)", example = "CRO-SP 123456")
    private String cro;

    @Size(max = 100, message = "Specialty must not exceed 100 characters")
    @Schema(description = "Dental specialty", example = "Ortodontia")
    private String specialty;
}
