package com.task.paydaytrade.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.task.paydaytrade.utility.Constant.JWT_HEADER;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springOpenApiConfig() {
        return new OpenAPI()
                .info(new Info().license(new License().url("http://localhost:8080/swagger-ui/index.html/")))
                .addSecurityItem(new SecurityRequirement().addList(JWT_HEADER))
                .components(new Components().addSecuritySchemes(JWT_HEADER, apiKeySecuritySchema()));
    }

    public SecurityScheme apiKeySecuritySchema() {
        return new SecurityScheme()
                .name(JWT_HEADER)
                .description("Add token which you have got as a response from Login endpoint")
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.APIKEY);
    }

}