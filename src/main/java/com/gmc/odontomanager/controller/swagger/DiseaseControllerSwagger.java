package com.gmc.odontomanager.controller.swagger;

import com.gmc.odontomanager.entity.dtos.DiseaseRequestDTO;
import com.gmc.odontomanager.entity.dtos.DiseaseResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Diseases", description = "Operations related to medical conditions, allergies and comorbidities catalog")
public interface DiseaseControllerSwagger {

    @Operation(summary = "List all diseases with pagination", description = "Returns a paginated list of cataloged diseases and comorbidities")
    @ApiResponse(responseCode = "200", description = "Page of diseases retrieved successfully")
    ResponseEntity<Page<DiseaseResponseDTO>> findAllDisease(@ParameterObject Pageable pageable);

    @Operation(summary = "Find disease by ID", description = "Retrieves details of a disease entry by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disease found successfully"),
            @ApiResponse(responseCode = "404", description = "Disease not found")
    })
    ResponseEntity<DiseaseResponseDTO> findDiseaseById(@Parameter(description = "ID of the disease to retrieve", required = true) Long id);

    @Operation(summary = "Create a new disease", description = "Adds a new disease / condition to the clinical catalog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Disease created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or disease name already exists")
    })
    ResponseEntity<DiseaseResponseDTO> createDisease(@Valid @RequestBody DiseaseRequestDTO requestDto);

    @Operation(summary = "Update an existing disease", description = "Updates name of a cataloged disease")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disease updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or duplicate disease name"),
            @ApiResponse(responseCode = "404", description = "Disease not found")
    })
    ResponseEntity<DiseaseResponseDTO> updateDisease(@Parameter(description = "ID of the disease to update", required = true) @PathVariable Long id, @Valid @RequestBody DiseaseRequestDTO requestDto);

    @Operation(summary = "Delete a disease", description = "Removes a disease from the catalog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Disease deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Disease not found")
    })
    ResponseEntity<Void> deleteDisease(@Parameter(description = "ID of the disease to delete", required = true) @PathVariable Long id);
}
