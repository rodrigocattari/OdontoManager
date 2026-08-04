package com.gmc.odontomanager.entity;

import com.gmc.odontomanager.entity.base.Auditable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "anamnesis")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Anamnesis extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Version
    @Column(name = "version")
    private Integer version;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    @Column(name = "recorded_by", length = 150)
    private String recordedBy;

    @Column(name = "responsible_name", length = 200)
    private String responsibleName;

    @Column(name = "reason_for_visit", length = 1000)
    private String reasonForVisit;

    @Column(name = "flossing_frequency_description", length = 200)
    private String flossingFrequencyDescription;

    @Column(name = "brush_count_per_day")
    private Integer brushCountPerDay;

    // Boolean fields
    @Column(name = "satisfied_with_teeth")
    private Boolean satisfiedWithTeeth;

    @Column(name = "is_pregnant")
    private Boolean isPregnant;

    @Column(name = "uses_drugs")
    private Boolean usesDrugs;

    @Column(name = "is_smoker")
    private Boolean isSmoker;

    @Column(name = "uses_respirator")
    private Boolean usesRespirator;

    @Column(name = "is_alcoholic")
    private Boolean isAlcoholic;

    @Column(name = "teeth_grinding")
    private Boolean teethGrinding;

    @Column(name = "regular_physical_activity")
    private Boolean regularPhysicalActivity;

    @Column(name = "tongue_cleaning")
    private Boolean tongueCleaning;

    @Column(name = "facial_asymmetry")
    private Boolean facialAsymmetry;

    @Column(name = "tmj_disorder")
    private Boolean tmjDisorder;

    @Column(name = "salivary_gland_swelling")
    private Boolean salivaryGlandSwelling;

    // Long text fields
    @Column(name = "medications_description", columnDefinition = "TEXT")
    private String medicationsDescription;

    @Column(name = "pacemaker_description", columnDefinition = "TEXT")
    private String pacemakerDescription;

    @Column(name = "bleeding_description", columnDefinition = "TEXT")
    private String bleedingDescription;

    @Column(name = "obstetric_complication_description", columnDefinition = "TEXT")
    private String obstetricComplicationDescription;

    @Column(name = "recent_stress_description", columnDefinition = "TEXT")
    private String recentStressDescription;

    @Column(name = "mouthwash_description", columnDefinition = "TEXT")
    private String mouthwashDescription;

    @Column(name = "lips_description", columnDefinition = "TEXT")
    private String lipsDescription;

    @Column(name = "mucosa_description", columnDefinition = "TEXT")
    private String mucosaDescription;

    @Column(name = "tongue_description", columnDefinition = "TEXT")
    private String tongueDescription;

    @Column(name = "gum_description", columnDefinition = "TEXT")
    private String gumDescription;

    @Column(name = "palate_description", columnDefinition = "TEXT")
    private String palateDescription;

    @Column(name = "general_observations", columnDefinition = "TEXT")
    private String generalObservations;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Employee doctor;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "anamnesis_disease",
            joinColumns = @JoinColumn(name = "anamnesis_id"),
            inverseJoinColumns = @JoinColumn(name = "disease_id")
    )
    @Builder.Default
    private Set<Disease> diseases = new HashSet<>();
}
