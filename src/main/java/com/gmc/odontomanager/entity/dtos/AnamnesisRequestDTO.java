package com.gmc.odontomanager.entity.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Payload for creating or updating an Anamnesis clinical form")
public class AnamnesisRequestDTO {

    @NotNull(message = "Patient ID is required")
    @Schema(description = "ID of the patient associated with the anamnesis", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long patientId;

    @Schema(description = "ID of the employee/doctor responsible for the anamnesis", example = "2")
    private Long doctorId;

    @Schema(description = "List of disease IDs identified for this patient", example = "[1, 3]")
    private List<Long> diseaseIds;

    @NotNull(message = "Recorded at timestamp is required")
    @Schema(description = "Timestamp when the anamnesis was conducted", example = "2026-08-03T14:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime recordedAt;

    @Size(max = 150, message = "Recorded by must not exceed 150 characters")
    @Schema(description = "Name of professional who recorded the anamnesis", example = "Dr. Carlos Eduardo Lima")
    private String recordedBy;

    @Size(max = 200, message = "Responsible name must not exceed 200 characters")
    @Schema(description = "Legal guardian or companion name (if applicable)", example = "Jane Doe")
    private String responsibleName;

    @Size(max = 1000, message = "Reason for visit must not exceed 1000 characters")
    @Schema(description = "Chief complaint / Reason for clinical visit", example = "Sensitivity in lower molars and bleeding gums")
    private String reasonForVisit;

    @Size(max = 200, message = "Flossing frequency must not exceed 200 characters")
    @Schema(description = "Frequency and habits of dental flossing", example = "Once daily before sleep")
    private String flossingFrequencyDescription;

    @Schema(description = "Number of times teeth are brushed per day", example = "3")
    private Integer brushCountPerDay;

    // Boolean Clinical Indicators
    @Schema(description = "Indicates if the patient is satisfied with their teeth aesthetics and function", example = "false")
    private Boolean satisfiedWithTeeth;

    @Schema(description = "Indicates whether the patient is currently pregnant", example = "false")
    private Boolean isPregnant;

    @Schema(description = "Indicates illicit drug consumption", example = "false")
    private Boolean usesDrugs;

    @Schema(description = "Indicates active smoking habit", example = "false")
    private Boolean isSmoker;

    @Schema(description = "Indicates reliance on mouth breathing or mechanical respirator", example = "false")
    private Boolean usesRespirator;

    @Schema(description = "Indicates regular or excessive alcohol intake", example = "false")
    private Boolean isAlcoholic;

    @Schema(description = "Indicates bruxism / teeth grinding habits", example = "true")
    private Boolean teethGrinding;

    @Schema(description = "Indicates regular practice of physical exercise", example = "true")
    private Boolean regularPhysicalActivity;

    @Schema(description = "Indicates habit of daily tongue cleaning/scraping", example = "true")
    private Boolean tongueCleaning;

    @Schema(description = "Indicates presence of facial asymmetry", example = "false")
    private Boolean facialAsymmetry;

    @Schema(description = "Indicates Temporomandibular Joint (TMJ) dysfunction", example = "true")
    private Boolean tmjDisorder;

    @Schema(description = "Indicates swelling or inflammation in salivary glands", example = "false")
    private Boolean salivaryGlandSwelling;

    // Long Text Clinical Observations
    @Schema(description = "Current medications in use and dosages", example = "Losartan 50mg 1x/day, Metformin 850mg 2x/day")
    private String medicationsDescription;

    @Schema(description = "Pacemaker presence and cardiac history details", example = "Dual-chamber pacemaker implanted in 2021")
    private String pacemakerDescription;

    @Schema(description = "Abnormal bleeding or anticoagulant history", example = "Takes Aspirin Prevent, slight prolonged bleeding")
    private String bleedingDescription;

    @Schema(description = "History of obstetric complications in past or current pregnancies", example = "None reported")
    private String obstetricComplicationDescription;

    @Schema(description = "Recent episodes of acute stress or anxiety", example = "Work-related anxiety, causing sleep disruption")
    private String recentStressDescription;

    @Schema(description = "Mouthwash brand and usage frequency", example = "Alcohol-free chlorhexidine 0.12% occasionally")
    private String mouthwashDescription;

    @Schema(description = "Clinical evaluation of lips (color, lesions, texture)", example = "Hydrated, no lesions or cheilitis observed")
    private String lipsDescription;

    @Schema(description = "Clinical evaluation of oral mucosa", example = "Normochromic oral mucosa, intact lining")
    private String mucosaDescription;

    @Schema(description = "Clinical evaluation of tongue (coating, papillae)", example = "Slight white coating on dorsum")
    private String tongueDescription;

    @Schema(description = "Clinical evaluation of gums (gingivitis, bleeding on probing)", example = "Marginal gingivitis in lower anteriors")
    private String gumDescription;

    @Schema(description = "Clinical evaluation of hard and soft palate", example = "Intact, normal anatomical appearance")
    private String palateDescription;

    @Schema(description = "General clinical notes and dentist observations", example = "Patient anxious regarding local anesthesia; recommend gentle approach.")
    private String generalObservations;
}
