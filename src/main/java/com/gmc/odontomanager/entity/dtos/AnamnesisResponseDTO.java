package com.gmc.odontomanager.entity.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detailed response for Anamnesis clinical form")
public class AnamnesisResponseDTO {

    @Schema(description = "Anamnesis record ID", example = "1")
    private Long id;

    @Schema(description = "Optimistic locking version", example = "0")
    private Integer version;

    @Schema(description = "Patient details")
    private PatientSummaryDTO patient;

    @Schema(description = "Doctor / Employee details")
    private EmployeeSummaryDTO doctor;

    @Schema(description = "Associated diseases / comorbidities")
    private Set<DiseaseResponseDTO> diseases;

    @Schema(description = "Timestamp of anamnesis record", example = "2026-08-03T14:30:00")
    private LocalDateTime recordedAt;

    @Schema(description = "Recorded by", example = "Dr. Carlos Eduardo Lima")
    private String recordedBy;

    @Schema(description = "Responsible name", example = "Jane Doe")
    private String responsibleName;

    @Schema(description = "Reason for clinical visit", example = "Sensitivity in lower molars and bleeding gums")
    private String reasonForVisit;

    @Schema(description = "Flossing frequency description", example = "Once daily before sleep")
    private String flossingFrequencyDescription;

    @Schema(description = "Brush count per day", example = "3")
    private Integer brushCountPerDay;

    // Boolean indicators
    @Schema(description = "Satisfied with teeth aesthetics/function", example = "false")
    private Boolean satisfiedWithTeeth;

    @Schema(description = "Is pregnant", example = "false")
    private Boolean isPregnant;

    @Schema(description = "Uses illicit drugs", example = "false")
    private Boolean usesDrugs;

    @Schema(description = "Is smoker", example = "false")
    private Boolean isSmoker;

    @Schema(description = "Uses respirator", example = "false")
    private Boolean usesRespirator;

    @Schema(description = "Is alcoholic", example = "false")
    private Boolean isAlcoholic;

    @Schema(description = "Teeth grinding (bruxism)", example = "true")
    private Boolean teethGrinding;

    @Schema(description = "Regular physical activity", example = "true")
    private Boolean regularPhysicalActivity;

    @Schema(description = "Tongue cleaning habit", example = "true")
    private Boolean tongueCleaning;

    @Schema(description = "Facial asymmetry detected", example = "false")
    private Boolean facialAsymmetry;

    @Schema(description = "TMJ disorder detected", example = "true")
    private Boolean tmjDisorder;

    @Schema(description = "Salivary gland swelling", example = "false")
    private Boolean salivaryGlandSwelling;

    // Long text observations
    @Schema(description = "Medications description")
    private String medicationsDescription;

    @Schema(description = "Pacemaker description")
    private String pacemakerDescription;

    @Schema(description = "Bleeding description")
    private String bleedingDescription;

    @Schema(description = "Obstetric complication description")
    private String obstetricComplicationDescription;

    @Schema(description = "Recent stress description")
    private String recentStressDescription;

    @Schema(description = "Mouthwash description")
    private String mouthwashDescription;

    @Schema(description = "Lips description")
    private String lipsDescription;

    @Schema(description = "Mucosa description")
    private String mucosaDescription;

    @Schema(description = "Tongue description")
    private String tongueDescription;

    @Schema(description = "Gum description")
    private String gumDescription;

    @Schema(description = "Palate description")
    private String palateDescription;

    @Schema(description = "General observations")
    private String generalObservations;

    @Schema(description = "Record creation timestamp", example = "2026-08-03T14:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Record last modification timestamp", example = "2026-08-03T14:30:00")
    private LocalDateTime updatedAt;
}
