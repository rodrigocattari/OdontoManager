package com.gmc.odontomanager.controller.swagger;

import com.gmc.odontomanager.entity.dtos.PatientRequestDTO;
import com.gmc.odontomanager.entity.dtos.PatientResponseDTO;
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

@Tag(name = "Patients", description = "Operations related to patient registration, updates and queries")
public interface PatientControllerSwagger {

    @Operation(summary = "List all patients with pagination", description = "Returns a paginated list of registered patients")
    @ApiResponse(responseCode = "200", description = "Page of patients retrieved successfully")
    ResponseEntity<Page<PatientResponseDTO>> findAllPatient(@ParameterObject Pageable pageable);

    @Operation(summary = "Find patient by ID", description = "Retrieves full details of a single patient by their unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient found successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    ResponseEntity<PatientResponseDTO> findPatientById(@Parameter(description = "ID of the patient to retrieve", required = true) Long id);

    @Operation(summary = "Create a new patient", description = "Registers a new patient in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or duplicate CPF/Email")
    })
    ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO requestDto);

    @Operation(summary = "Update an existing patient", description = "Updates personal, contact or address information of a patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or duplicate CPF/Email"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    ResponseEntity<PatientResponseDTO> updatePatient(@Parameter(description = "ID of the patient to update", required = true) @PathVariable Long id, @Valid @RequestBody PatientRequestDTO requestDto);

    @Operation(summary = "Delete a patient", description = "Removes a patient from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    ResponseEntity<Void> deletePatient(@Parameter(description = "ID of the patient to delete", required = true) @PathVariable Long id);
}