package com.gmc.odontomanager.controller.swagger;

import com.gmc.odontomanager.entity.dtos.EmployeeRequestDTO;
import com.gmc.odontomanager.entity.dtos.EmployeeResponseDTO;
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

@Tag(name = "Employees", description = "Operations related to clinic staff, dentists and employee management")
public interface EmployeeControllerSwagger {

    @Operation(summary = "List all employees with pagination", description = "Returns a paginated list of clinic employees and dentists")
    @ApiResponse(responseCode = "200", description = "Page of employees retrieved successfully")
    ResponseEntity<Page<EmployeeResponseDTO>> findAllEmployee(@ParameterObject Pageable pageable);

    @Operation(summary = "Find employee by ID", description = "Retrieves details of a single employee by their unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    ResponseEntity<EmployeeResponseDTO> findEmployeeById(@Parameter(description = "ID of the employee to retrieve", required = true) Long id);

    @Operation(summary = "Create a new employee", description = "Registers a new doctor or staff member in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or duplicate CRO/CPF/Email")
    })
    ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO requestDto);

    @Operation(summary = "Update an existing employee", description = "Updates professional or personal information of an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or duplicate CRO/CPF/Email"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    ResponseEntity<EmployeeResponseDTO> updateEmployee(@Parameter(description = "ID of the employee to update", required = true) @PathVariable Long id, @Valid @RequestBody EmployeeRequestDTO requestDto);

    @Operation(summary = "Delete an employee", description = "Removes an employee from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    ResponseEntity<Void> deleteEmployee(@Parameter(description = "ID of the employee to delete", required = true) @PathVariable Long id);
}
