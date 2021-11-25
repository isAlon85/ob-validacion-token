package com.team1.obvalidacion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiDetails())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiDetails(){
        return new ApiInfo("Proyecto Validación, de OpenBootcamp",
                "Library API REST docs",
                "1.0",
                "http://www.google.com",
                new Contact("Equipo 4",
                        "https://www.google.com",
                        "open-b-validacion@gmail.com"),
                "OB SA",
                "http://www.google.com",
                Collections.emptyList());
    }
}