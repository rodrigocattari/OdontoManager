package com.gmc.odontomanager.mapper;

import com.gmc.odontomanager.entity.Disease;
import com.gmc.odontomanager.entity.dtos.DiseaseRequestDTO;
import com.gmc.odontomanager.entity.dtos.DiseaseResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class DiseaseMapper {

    public Disease toEntity(DiseaseRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return Disease.builder()
                .name(dto.getName())
                .build();
    }

    public DiseaseResponseDTO toResponseDTO(Disease entity) {
        if (entity == null) {
            return null;
        }

        return DiseaseResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public void updateEntityFromDTO(DiseaseRequestDTO dto, Disease entity) {
        if (dto == null || entity == null) {
            return;
        }

        if (dto.getName() != null) entity.setName(dto.getName());
    }
}
