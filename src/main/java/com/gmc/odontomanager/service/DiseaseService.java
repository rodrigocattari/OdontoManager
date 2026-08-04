package com.gmc.odontomanager.service;

import com.gmc.odontomanager.entity.Disease;
import com.gmc.odontomanager.entity.dtos.DiseaseRequestDTO;
import com.gmc.odontomanager.entity.dtos.DiseaseResponseDTO;
import com.gmc.odontomanager.exeption.ResourceNotFoundException;
import com.gmc.odontomanager.mapper.DiseaseMapper;
import com.gmc.odontomanager.repository.DiseaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;
    private final DiseaseMapper diseaseMapper;

    public DiseaseService(DiseaseRepository diseaseRepository, DiseaseMapper diseaseMapper) {
        this.diseaseRepository = diseaseRepository;
        this.diseaseMapper = diseaseMapper;
    }

    @Transactional(readOnly = true)
    public Page<DiseaseResponseDTO> findAll(Pageable pageable) {
        return diseaseRepository.findAll(pageable)
                .map(diseaseMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public DiseaseResponseDTO findById(Long id) {
        return diseaseRepository.findById(id)
                .map(diseaseMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Disease not found with ID: " + id));
    }

    @Transactional
    public DiseaseResponseDTO create(DiseaseRequestDTO requestDto) {
        if (diseaseRepository.existsByName(requestDto.getName())) {
            throw new IllegalArgumentException("A disease with name '" + requestDto.getName() + "' already exists.");
        }

        Disease disease = diseaseMapper.toEntity(requestDto);
        disease = diseaseRepository.save(disease);
        return diseaseMapper.toResponseDTO(disease);
    }

    @Transactional
    public DiseaseResponseDTO update(Long id, DiseaseRequestDTO requestDto) {
        Disease disease = diseaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disease not found with ID: " + id));

        if (diseaseRepository.existsByNameAndIdNot(requestDto.getName(), id)) {
            throw new IllegalArgumentException("Another disease with name '" + requestDto.getName() + "' already exists.");
        }

        diseaseMapper.updateEntityFromDTO(requestDto, disease);
        disease = diseaseRepository.save(disease);
        return diseaseMapper.toResponseDTO(disease);
    }

    @Transactional
    public void delete(Long id) {
        Disease disease = diseaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disease not found with ID: " + id));

        diseaseRepository.delete(disease);
    }
}
