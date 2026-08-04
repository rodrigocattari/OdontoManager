package com.gmc.odontomanager.repository;

import com.gmc.odontomanager.entity.Anamnesis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnamnesisRepository extends JpaRepository<Anamnesis, Long> {

    Page<Anamnesis> findByPatientId(Long patientId, Pageable pageable);

    Page<Anamnesis> findByDoctorId(Long doctorId, Pageable pageable);
}
