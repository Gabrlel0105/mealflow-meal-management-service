package com.gabrielmamani.mealmanagement.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configures the OpenAPI specification exposed by the platform.
 */
@Configuration
public class OpenApiConfiguration {
    // Properties
    @Value("${spring.application.name}")
    String applicationName;

    @Value("${documentation.application.description}")
    String applicationDescription;

    @Value("${documentation.application.version}")
    String applicationVersion;

    @Value("${documentation.application.contact.name}")
    String contactName;

    @Value("${documentation.application.contact.email}")
    String contactEmail;

    // Methods

    /**
     * Builds the OpenAPI document used by Swagger UI and client generation tools.
     *
     * @return configured OpenAPI descriptor
     */
    @Bean
    public OpenAPI learningPlatformOpenApi() {

        // General configuration
        var openApi = new OpenAPI();
        openApi
                .info(new Info()
                        .title(this.applicationName)
                        .description(this.applicationDescription)
                        .version(this.applicationVersion)
                        .contact(new Contact()
                                .name(this.contactName)
                                .email(this.contactEmail))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("Mealflow Platform Documentation")
                        .url("https://mealflow.devscracks.com/docs"));

        // Add server configurations
        openApi.servers(List.of(
                new Server()
                        .url("http://localhost:8085")
                        .description("Local Development Environment")
        ));

        // Security schemes: identity is resolved upstream (API Gateway) and forwarded as headers
        final String userIdSchemeName = "X-User-Id";
        final String userRoleSchemeName = "X-User-Role";

        openApi.addSecurityItem(new SecurityRequirement()
                        .addList(userIdSchemeName)
                        .addList(userRoleSchemeName))
                .components(new Components()
                        .addSecuritySchemes(userIdSchemeName,
                                new SecurityScheme()
                                        .name(userIdSchemeName)
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .description("Identifier of the authenticated user, forwarded by the API Gateway"))
                        .addSecuritySchemes(userRoleSchemeName,
                                new SecurityScheme()
                                        .name(userRoleSchemeName)
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .description("Role of the authenticated user: ROLE_CONSUMER or ROLE_PROVIDER")));

        return openApi;
    }
}