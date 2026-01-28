package com.gmc.odontomanager.entity.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientDTO {
    private String name;
    private String email;
    private String phone;
    private String cpf;
    private LocalDate dateOfBirth;
    private Short isActive;
}
