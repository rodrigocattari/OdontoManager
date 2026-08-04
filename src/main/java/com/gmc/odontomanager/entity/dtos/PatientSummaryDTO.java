package com.gmc.odontomanager.entity.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Summary representation of a Patient")
public class PatientSummaryDTO {

    @Schema(description = "Patient ID", example = "1")
    private Long id;

    @Schema(description = "Patient full name", example = "Maria Silva Santos")
    private String name;

    @Schema(description = "Patient CPF", example = "123.456.789-00")
    private String cpf;

    @Schema(description = "Patient email", example = "maria.silva@email.com")
    private String email;

    @Schema(description = "Patient phone", example = "+55 11 98765-4321")
    private String phone;
}
