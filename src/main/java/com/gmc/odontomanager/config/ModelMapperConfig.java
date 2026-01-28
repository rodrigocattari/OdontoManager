package com.gmc.odontomanager.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // Configuração crucial para o seu caso: ignora campos nulos no mapeamento
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }
}