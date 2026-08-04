package com.gmc.odontomanager.controller.swagger;

import com.gmc.odontomanager.entity.dtos.AnamnesisRequestDTO;
import com.gmc.odontomanager.entity.dtos.AnamnesisResponseDTO;
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

@Tag(name = "Anamnesis", description = "Operations related to patient clinical anamnesis and health questionnaire records")
public interface AnamnesisControllerSwagger {

    @Operation(summary = "List all anamnesis records with pagination", description = "Returns a paginated list of all clinical anamnesis records")
    @ApiResponse(responseCode = "200", description = "Page of anamnesis records retrieved successfully")
    ResponseEntity<Page<AnamnesisResponseDTO>> findAllAnamnesis(@ParameterObject Pageable pageable);

    @Operation(summary = "Find anamnesis by ID", description = "Retrieves full details of an anamnesis record including patient, doctor and comorbidity information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anamnesis record found successfully"),
            @ApiResponse(responseCode = "404", description = "Anamnesis record not found")
    })
    ResponseEntity<AnamnesisResponseDTO> findAnamnesisById(@Parameter(description = "ID of the anamnesis record to retrieve", required = true) Long id);

    @Operation(summary = "List anamnesis records by patient ID", description = "Returns all historical anamnesis records associated with a specific patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient anamnesis records retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    ResponseEntity<Page<AnamnesisResponseDTO>> findAnamnesisByPatientId(
            @Parameter(description = "ID of the patient", required = true) @PathVariable Long patientId,
            @ParameterObject Pageable pageable
    );

    @Operation(summary = "Create a new anamnesis record", description = "Creates and links a new clinical anamnesis form to a patient, doctor and comorbidities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Anamnesis created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "404", description = "Patient, doctor or disease not found")
    })
    ResponseEntity<AnamnesisResponseDTO> createAnamnesis(@Valid @RequestBody AnamnesisRequestDTO requestDto);

    @Operation(summary = "Update an existing anamnesis record", description = "Updates clinical observations and answers of an anamnesis record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anamnesis updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "404", description = "Anamnesis record, patient, doctor or disease not found")
    })
    ResponseEntity<AnamnesisResponseDTO> updateAnamnesis(
            @Parameter(description = "ID of the anamnesis record to update", required = true) @PathVariable Long id,
            @Valid @RequestBody AnamnesisRequestDTO requestDto
    );

    @Operation(summary = "Delete an anamnesis record", description = "Removes an anamnesis record from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Anamnesis deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Anamnesis record not found")
    })
    ResponseEntity<Void> deleteAnamnesis(@Parameter(description = "ID of the anamnesis record to delete", required = true) @PathVariable Long id);
}
