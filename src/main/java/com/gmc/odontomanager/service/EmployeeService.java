package com.gmc.odontomanager.service;

import com.gmc.odontomanager.entity.Employee;
import com.gmc.odontomanager.entity.dtos.EmployeeRequestDTO;
import com.gmc.odontomanager.entity.dtos.EmployeeResponseDTO;
import com.gmc.odontomanager.exeption.ResourceNotFoundException;
import com.gmc.odontomanager.mapper.EmployeeMapper;
import com.gmc.odontomanager.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Transactional(readOnly = true)
    public Page<EmployeeResponseDTO> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(employeeMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public EmployeeResponseDTO findById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Employee/Doctor not found with ID: " + id));
    }

    @Transactional
    public EmployeeResponseDTO create(EmployeeRequestDTO requestDto) {
        if (requestDto.getCro() != null && !requestDto.getCro().isBlank() && employeeRepository.existsByCro(requestDto.getCro())) {
            throw new IllegalArgumentException("An employee with CRO '" + requestDto.getCro() + "' already exists.");
        }

        if (requestDto.getCpf() != null && !requestDto.getCpf().isBlank() && employeeRepository.existsByCpf(requestDto.getCpf())) {
            throw new IllegalArgumentException("An employee with CPF '" + requestDto.getCpf() + "' already exists.");
        }

        if (requestDto.getEmail() != null && !requestDto.getEmail().isBlank() && employeeRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("An employee with email '" + requestDto.getEmail() + "' already exists.");
        }

        Employee employee = employeeMapper.toEntity(requestDto);
        employee = employeeRepository.save(employee);
        return employeeMapper.toResponseDTO(employee);
    }

    @Transactional
    public EmployeeResponseDTO update(Long id, EmployeeRequestDTO requestDto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee/Doctor not found with ID: " + id));

        if (requestDto.getCro() != null && !requestDto.getCro().isBlank() && employeeRepository.existsByCroAndIdNot(requestDto.getCro(), id)) {
            throw new IllegalArgumentException("Another employee with CRO '" + requestDto.getCro() + "' already exists.");
        }

        if (requestDto.getCpf() != null && !requestDto.getCpf().isBlank() && employeeRepository.existsByCpfAndIdNot(requestDto.getCpf(), id)) {
            throw new IllegalArgumentException("Another employee with CPF '" + requestDto.getCpf() + "' already exists.");
        }

        if (requestDto.getEmail() != null && !requestDto.getEmail().isBlank() && employeeRepository.existsByEmailAndIdNot(requestDto.getEmail(), id)) {
            throw new IllegalArgumentException("Another employee with email '" + requestDto.getEmail() + "' already exists.");
        }

        employeeMapper.updateEntityFromDTO(requestDto, employee);
        employee = employeeRepository.save(employee);
        return employeeMapper.toResponseDTO(employee);
    }

    @Transactional
    public void delete(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee/Doctor not found with ID: " + id));

        employeeRepository.delete(employee);
    }
}
