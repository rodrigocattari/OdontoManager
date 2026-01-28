package com.gmc.odontomanager.controller;

import com.gmc.odontomanager.controller.swagger.DiseaseControllerSwagger;
import com.gmc.odontomanager.entity.Disease;
import com.gmc.odontomanager.entity.Patient;
import com.gmc.odontomanager.entity.dtos.PatientDTO;
import com.gmc.odontomanager.exeption.ResourceNotFoundException;
import com.gmc.odontomanager.repository.DiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/disease")
public class DiseaseController implements DiseaseControllerSwagger {

    private final DiseaseRepository diseaseRepository;

    public DiseaseController(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }

    @GetMapping
    public ResponseEntity<Page<Disease>> findAllDisease(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Disease> patients = diseaseRepository.findAll(pageable);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disease> findDiseaseById(@PathVariable Long id) {
        return diseaseRepository.findById(id)
                .map(disease -> ResponseEntity.ok().body(disease))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Disease createDisease(@RequestBody Disease disease) {
        return diseaseRepository.save(disease);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Disease> updateDisease(@PathVariable Long id, @RequestBody Disease diseaseDetails) {
        return diseaseRepository.findById(id)
                .map(disease -> {
                    disease.setName(diseaseDetails.getName());
                    Disease updated = diseaseRepository.save(disease);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisease(@PathVariable Long id) {
        return diseaseRepository.findById(id)
                .map(disease -> {
                    diseaseRepository.delete(disease);
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
