package com.gmc.odontomanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class SwaggerConfig {

    @Value("${app.name:OdontoManager}")
    private String appName;

    @Value("${app.version:1.0.0}")
    private String appVersion;

    @Value("${app.description:API OdontoManager}")
    private String appDescription;

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("OdontoManager")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        String dataHora = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());

        return new OpenAPI().info(
                new Info()
                        .title(appName)
                        .version(appVersion)
                        .description(appDescription + " | Gerado em: " + dataHora)
                        .contact(new Contact().name("GMC").email("seu-email@dominio.com"))
        );
    }
}
