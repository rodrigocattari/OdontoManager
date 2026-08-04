package com.gmc.odontomanager.controller;

import com.gmc.odontomanager.controller.swagger.DiseaseControllerSwagger;
import com.gmc.odontomanager.entity.dtos.DiseaseRequestDTO;
import com.gmc.odontomanager.entity.dtos.DiseaseResponseDTO;
import com.gmc.odontomanager.service.DiseaseService;
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
@RequestMapping("/api/disease")
public class DiseaseController implements DiseaseControllerSwagger {

    private final DiseaseService diseaseService;

    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<DiseaseResponseDTO>> findAllDisease(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<DiseaseResponseDTO> diseases = diseaseService.findAll(pageable);
        return ResponseEntity.ok(diseases);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<DiseaseResponseDTO> findDiseaseById(@PathVariable Long id) {
        DiseaseResponseDTO disease = diseaseService.findById(id);
        return ResponseEntity.ok(disease);
    }

    @Override
    @PostMapping
    public ResponseEntity<DiseaseResponseDTO> createDisease(@Valid @RequestBody DiseaseRequestDTO requestDto) {
        DiseaseResponseDTO createdDisease = diseaseService.create(requestDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDisease.getId())
                .toUri();
        return ResponseEntity.created(uri).body(createdDisease);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<DiseaseResponseDTO> updateDisease(
            @PathVariable Long id,
            @Valid @RequestBody DiseaseRequestDTO requestDto
    ) {
        DiseaseResponseDTO updatedDisease = diseaseService.update(id, requestDto);
        return ResponseEntity.ok(updatedDisease);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisease(@PathVariable Long id) {
        diseaseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
