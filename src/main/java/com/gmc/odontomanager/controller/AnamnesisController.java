package com.gmc.odontomanager.controller;

import com.gmc.odontomanager.controller.swagger.AnamnesisControllerSwagger;
import com.gmc.odontomanager.entity.dtos.AnamnesisRequestDTO;
import com.gmc.odontomanager.entity.dtos.AnamnesisResponseDTO;
import com.gmc.odontomanager.service.AnamnesisService;
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
@RequestMapping("/api/anamnesis")
public class AnamnesisController implements AnamnesisControllerSwagger {

    private final AnamnesisService anamnesisService;

    public AnamnesisController(AnamnesisService anamnesisService) {
        this.anamnesisService = anamnesisService;
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<AnamnesisResponseDTO>> findAllAnamnesis(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<AnamnesisResponseDTO> page = anamnesisService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AnamnesisResponseDTO> findAnamnesisById(@PathVariable Long id) {
        AnamnesisResponseDTO responseDto = anamnesisService.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @Override
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Page<AnamnesisResponseDTO>> findAnamnesisByPatientId(
            @PathVariable Long patientId,
            @PageableDefault(page = 0, size = 10, sort = "recordedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<AnamnesisResponseDTO> page = anamnesisService.findByPatientId(patientId, pageable);
        return ResponseEntity.ok(page);
    }

    @Override
    @PostMapping
    public ResponseEntity<AnamnesisResponseDTO> createAnamnesis(@Valid @RequestBody AnamnesisRequestDTO requestDto) {
        AnamnesisResponseDTO createdAnamnesis = anamnesisService.create(requestDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdAnamnesis.getId())
                .toUri();
        return ResponseEntity.created(uri).body(createdAnamnesis);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AnamnesisResponseDTO> updateAnamnesis(
            @PathVariable Long id,
            @Valid @RequestBody AnamnesisRequestDTO requestDto
    ) {
        AnamnesisResponseDTO updatedAnamnesis = anamnesisService.update(id, requestDto);
        return ResponseEntity.ok(updatedAnamnesis);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnamnesis(@PathVariable Long id) {
        anamnesisService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
