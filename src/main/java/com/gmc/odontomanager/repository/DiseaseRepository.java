package com.gmc.odontomanager.repository;

import com.gmc.odontomanager.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    Optional<Disease> findByName(String name);

    List<Disease> findAllByIdIn(Collection<Long> ids);
}
