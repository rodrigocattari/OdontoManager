package com.gmc.odontomanager.controller.swagger;

import com.gmc.odontomanager.entity.Patient;
import com.gmc.odontomanager.entity.dtos.PatientDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "Patient", description = "OdontoManager Patient Management")
public interface PatientControllerSwagger {

    @Operation(summary = "List all patient with pageble")
    ResponseEntity<Page<Patient>> findAllPatient(@ParameterObject Pageable pageable);

    @Operation(summary = "Find patient by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient found"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    ResponseEntity<Patient> findPatientById(@Parameter(description = "ID Patient") Long id);

    @Operation(summary = "Creat new patient")
    @ApiResponse(responseCode = "201", description = "Patient created with success")
    Patient createPatient(@RequestBody Patient patient);

    @Operation(summary = "Update patient")
    ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDto);

    @Operation(summary = "Delete patient")
    ResponseEntity<Void> deletePatient(@PathVariable Long id);
}