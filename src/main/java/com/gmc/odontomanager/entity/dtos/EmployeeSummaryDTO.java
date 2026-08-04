package com.gmc.odontomanager.entity.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Summary representation of an Employee / Doctor")
public class EmployeeSummaryDTO {

    @Schema(description = "Employee ID", example = "2")
    private Long id;

    @Schema(description = "Employee full name", example = "Dr. Carlos Eduardo Lima")
    private String name;

    @Schema(description = "CRO registration", example = "CRO-SP 123456")
    private String cro;

    @Schema(description = "Dental specialty", example = "Ortodontia")
    private String specialty;

    @Schema(description = "Contact phone", example = "+55 11 91234-5678")
    private String phone;
}
