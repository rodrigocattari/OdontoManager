package com.gmc.odontomanager.entity.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detailed response for Disease entity")
public class DiseaseResponseDTO {

    @Schema(description = "Unique identifier of the disease", example = "1")
    private Long id;

    @Schema(description = "Name of the disease / comorbidity", example = "Hipertensão Arterial")
    private String name;

    @Schema(description = "Record creation timestamp", example = "2026-08-03T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Record last modification timestamp", example = "2026-08-03T10:30:00")
    private LocalDateTime updatedAt;
}
