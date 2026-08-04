package com.gmc.odontomanager.mapper;

import com.gmc.odontomanager.entity.Anamnesis;
import com.gmc.odontomanager.entity.Disease;
import com.gmc.odontomanager.entity.Employee;
import com.gmc.odontomanager.entity.Patient;
import com.gmc.odontomanager.entity.dtos.AnamnesisRequestDTO;
import com.gmc.odontomanager.entity.dtos.AnamnesisResponseDTO;
import com.gmc.odontomanager.entity.dtos.DiseaseResponseDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AnamnesisMapper {

    private final PatientMapper patientMapper;
    private final EmployeeMapper employeeMapper;
    private final DiseaseMapper diseaseMapper;

    public AnamnesisMapper(PatientMapper patientMapper, EmployeeMapper employeeMapper, DiseaseMapper diseaseMapper) {
        this.patientMapper = patientMapper;
        this.employeeMapper = employeeMapper;
        this.diseaseMapper = diseaseMapper;
    }

    public Anamnesis toEntity(AnamnesisRequestDTO dto, Patient patient, Employee doctor, Set<Disease> diseases) {
        if (dto == null) {
            return null;
        }

        return Anamnesis.builder()
                .patient(patient)
                .doctor(doctor)
                .diseases(diseases != null ? diseases : new HashSet<>())
                .recordedAt(dto.getRecordedAt())
                .recordedBy(dto.getRecordedBy())
                .responsibleName(dto.getResponsibleName())
                .reasonForVisit(dto.getReasonForVisit())
                .flossingFrequencyDescription(dto.getFlossingFrequencyDescription())
                .brushCountPerDay(dto.getBrushCountPerDay())
                .satisfiedWithTeeth(dto.getSatisfiedWithTeeth())
                .isPregnant(dto.getIsPregnant())
                .usesDrugs(dto.getUsesDrugs())
                .isSmoker(dto.getIsSmoker())
                .usesRespirator(dto.getUsesRespirator())
                .isAlcoholic(dto.getIsAlcoholic())
                .teethGrinding(dto.getTeethGrinding())
                .regularPhysicalActivity(dto.getRegularPhysicalActivity())
                .tongueCleaning(dto.getTongueCleaning())
                .facialAsymmetry(dto.getFacialAsymmetry())
                .tmjDisorder(dto.getTmjDisorder())
                .salivaryGlandSwelling(dto.getSalivaryGlandSwelling())
                .medicationsDescription(dto.getMedicationsDescription())
                .pacemakerDescription(dto.getPacemakerDescription())
                .bleedingDescription(dto.getBleedingDescription())
                .obstetricComplicationDescription(dto.getObstetricComplicationDescription())
                .recentStressDescription(dto.getRecentStressDescription())
                .mouthwashDescription(dto.getMouthwashDescription())
                .lipsDescription(dto.getLipsDescription())
                .mucosaDescription(dto.getMucosaDescription())
                .tongueDescription(dto.getTongueDescription())
                .gumDescription(dto.getGumDescription())
                .palateDescription(dto.getPalateDescription())
                .generalObservations(dto.getGeneralObservations())
                .build();
    }

    public AnamnesisResponseDTO toResponseDTO(Anamnesis entity) {
        if (entity == null) {
            return null;
        }

        Set<DiseaseResponseDTO> diseaseDtos = entity.getDiseases() != null
                ? entity.getDiseases().stream().map(diseaseMapper::toResponseDTO).collect(Collectors.toSet())
                : Collections.emptySet();

        return AnamnesisResponseDTO.builder()
                .id(entity.getId())
                .version(entity.getVersion())
                .patient(patientMapper.toSummaryDTO(entity.getPatient()))
                .doctor(employeeMapper.toSummaryDTO(entity.getDoctor()))
                .diseases(diseaseDtos)
                .recordedAt(entity.getRecordedAt())
                .recordedBy(entity.getRecordedBy())
                .responsibleName(entity.getResponsibleName())
                .reasonForVisit(entity.getReasonForVisit())
                .flossingFrequencyDescription(entity.getFlossingFrequencyDescription())
                .brushCountPerDay(entity.getBrushCountPerDay())
                .satisfiedWithTeeth(entity.getSatisfiedWithTeeth())
                .isPregnant(entity.getIsPregnant())
                .usesDrugs(entity.getUsesDrugs())
                .isSmoker(entity.getIsSmoker())
                .usesRespirator(entity.getUsesRespirator())
                .isAlcoholic(entity.getIsAlcoholic())
                .teethGrinding(entity.getTeethGrinding())
                .regularPhysicalActivity(entity.getRegularPhysicalActivity())
                .tongueCleaning(entity.getTongueCleaning())
                .facialAsymmetry(entity.getFacialAsymmetry())
                .tmjDisorder(entity.getTmjDisorder())
                .salivaryGlandSwelling(entity.getSalivaryGlandSwelling())
                .medicationsDescription(entity.getMedicationsDescription())
                .pacemakerDescription(entity.getPacemakerDescription())
                .bleedingDescription(entity.getBleedingDescription())
                .obstetricComplicationDescription(entity.getObstetricComplicationDescription())
                .recentStressDescription(entity.getRecentStressDescription())
                .mouthwashDescription(entity.getMouthwashDescription())
                .lipsDescription(entity.getLipsDescription())
                .mucosaDescription(entity.getMucosaDescription())
                .tongueDescription(entity.getTongueDescription())
                .gumDescription(entity.getGumDescription())
                .palateDescription(entity.getPalateDescription())
                .generalObservations(entity.getGeneralObservations())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public void updateEntityFromDTO(AnamnesisRequestDTO dto, Anamnesis entity, Patient patient, Employee doctor, Set<Disease> diseases) {
        if (dto == null || entity == null) {
            return;
        }

        if (patient != null) entity.setPatient(patient);
        if (doctor != null) entity.setDoctor(doctor);
        if (diseases != null) entity.setDiseases(diseases);

        if (dto.getRecordedAt() != null) entity.setRecordedAt(dto.getRecordedAt());
        if (dto.getRecordedBy() != null) entity.setRecordedBy(dto.getRecordedBy());
        if (dto.getResponsibleName() != null) entity.setResponsibleName(dto.getResponsibleName());
        if (dto.getReasonForVisit() != null) entity.setReasonForVisit(dto.getReasonForVisit());
        if (dto.getFlossingFrequencyDescription() != null) entity.setFlossingFrequencyDescription(dto.getFlossingFrequencyDescription());
        if (dto.getBrushCountPerDay() != null) entity.setBrushCountPerDay(dto.getBrushCountPerDay());

        if (dto.getSatisfiedWithTeeth() != null) entity.setSatisfiedWithTeeth(dto.getSatisfiedWithTeeth());
        if (dto.getIsPregnant() != null) entity.setIsPregnant(dto.getIsPregnant());
        if (dto.getUsesDrugs() != null) entity.setUsesDrugs(dto.getUsesDrugs());
        if (dto.getIsSmoker() != null) entity.setIsSmoker(dto.getIsSmoker());
        if (dto.getUsesRespirator() != null) entity.setUsesRespirator(dto.getUsesRespirator());
        if (dto.getIsAlcoholic() != null) entity.setIsAlcoholic(dto.getIsAlcoholic());
        if (dto.getTeethGrinding() != null) entity.setTeethGrinding(dto.getTeethGrinding());
        if (dto.getRegularPhysicalActivity() != null) entity.setRegularPhysicalActivity(dto.getRegularPhysicalActivity());
        if (dto.getTongueCleaning() != null) entity.setTongueCleaning(dto.getTongueCleaning());
        if (dto.getFacialAsymmetry() != null) entity.setFacialAsymmetry(dto.getFacialAsymmetry());
        if (dto.getTmjDisorder() != null) entity.setTmjDisorder(dto.getTmjDisorder());
        if (dto.getSalivaryGlandSwelling() != null) entity.setSalivaryGlandSwelling(dto.getSalivaryGlandSwelling());

        if (dto.getMedicationsDescription() != null) entity.setMedicationsDescription(dto.getMedicationsDescription());
        if (dto.getPacemakerDescription() != null) entity.setPacemakerDescription(dto.getPacemakerDescription());
        if (dto.getBleedingDescription() != null) entity.setBleedingDescription(dto.getBleedingDescription());
        if (dto.getObstetricComplicationDescription() != null) entity.setObstetricComplicationDescription(dto.getObstetricComplicationDescription());
        if (dto.getRecentStressDescription() != null) entity.setRecentStressDescription(dto.getRecentStressDescription());
        if (dto.getMouthwashDescription() != null) entity.setMouthwashDescription(dto.getMouthwashDescription());
        if (dto.getLipsDescription() != null) entity.setLipsDescription(dto.getLipsDescription());
        if (dto.getMucosaDescription() != null) entity.setMucosaDescription(dto.getMucosaDescription());
        if (dto.getTongueDescription() != null) entity.setTongueDescription(dto.getTongueDescription());
        if (dto.getGumDescription() != null) entity.setGumDescription(dto.getGumDescription());
        if (dto.getPalateDescription() != null) entity.setPalateDescription(dto.getPalateDescription());
        if (dto.getGeneralObservations() != null) entity.setGeneralObservations(dto.getGeneralObservations());
    }
}
