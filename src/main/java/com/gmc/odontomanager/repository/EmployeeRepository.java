package com.gmc.odontomanager.repository;

import com.gmc.odontomanager.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByCro(String cro);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByCroAndIdNot(String cro, Long id);

    boolean existsByCpfAndIdNot(String cpf, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<Employee> findByCro(String cro);
}
