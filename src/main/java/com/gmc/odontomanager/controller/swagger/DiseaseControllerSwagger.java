package com.gmc.odontomanager.controller.swagger;

import com.gmc.odontomanager.entity.Disease;
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

@Tag(name = "Disease", description = "OdontoManager Disease Management")
public interface DiseaseControllerSwagger {

    @Operation(summary = "List all Disease with pageble")
    ResponseEntity<Page<Disease>> findAllDisease(@ParameterObject Pageable pageable);

    @Operation(summary = "Find Disease by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disease found"),
            @ApiResponse(responseCode = "404", description = "Disease not found")
    })
    ResponseEntity<Disease> findDiseaseById(@Parameter(description = "ID Disease") Long id);

    @Operation(summary = "Creat new Disease")
    @ApiResponse(responseCode = "201", description = "Disease created with success")
    Disease createDisease(@RequestBody Disease disease);

    @Operation(summary = "Update Disease")
    ResponseEntity<Disease> updateDisease(@PathVariable Long id, @RequestBody Disease Disease);

    @Operation(summary = "Delete Disease")
    ResponseEntity<Void> deleteDisease(@PathVariable Long id);
}

