package com.gmc.odontomanager.entity.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Payload for creating or updating a Disease")
public class DiseaseRequestDTO {

    @NotBlank(message = "Disease name is required")
    @Size(max = 150, message = "Disease name must not exceed 150 characters")
    @Schema(description = "Name of the disease / medical condition", example = "Diabetes Mellitus Tipo 2")
    private String name;
}
