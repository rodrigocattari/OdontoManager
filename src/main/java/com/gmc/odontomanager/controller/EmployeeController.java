package com.gmc.odontomanager.controller;

import com.gmc.odontomanager.controller.swagger.EmployeeControllerSwagger;
import com.gmc.odontomanager.entity.dtos.EmployeeRequestDTO;
import com.gmc.odontomanager.entity.dtos.EmployeeResponseDTO;
import com.gmc.odontomanager.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController implements EmployeeControllerSwagger {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<EmployeeResponseDTO>> findAllEmployee(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<EmployeeResponseDTO> employees = employeeService.findAll(pageable);
        return ResponseEntity.ok(employees);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> findEmployeeById(@PathVariable Long id) {
        EmployeeResponseDTO employee = employeeService.findById(id);
        return ResponseEntity.ok(employee);
    }

    @Override
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO requestDto) {
        EmployeeResponseDTO createdEmployee = employeeService.create(requestDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdEmployee.getId())
                .toUri();
        return ResponseEntity.created(uri).body(createdEmployee);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDTO requestDto
    ) {
        EmployeeResponseDTO updatedEmployee = employeeService.update(id, requestDto);
        return ResponseEntity.ok(updatedEmployee);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
