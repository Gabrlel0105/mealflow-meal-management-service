package com.devscracks.mealmanagement.shared.infrastructure.security.configuration;

import com.devscracks.mealmanagement.shared.infrastructure.security.authorization.HeaderAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configures HTTP security for the Meal Management service.
 *
 * <p>Authentication and role resolution are delegated to the upstream API Gateway, which
 * forwards the caller's identity via the {@code X-User-Id} and {@code X-User-Role} headers.
 * This service only enforces authorization on top of the identity carried by those headers.</p>
 */
@Configuration
public class SecurityConfiguration {

    private static final String[] PUBLIC_DOCUMENTATION_PATHS = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml"
    };

    private static final String MEALS_BASE_PATH = "/api/v1/meals";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, HeaderAuthenticationFilter headerAuthenticationFilter) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PUBLIC_DOCUMENTATION_PATHS).permitAll()
                        // Provider-only management endpoints
                        .requestMatchers(HttpMethod.GET, MEALS_BASE_PATH + "/provider").hasRole("PROVIDER")
                        .requestMatchers(HttpMethod.POST, MEALS_BASE_PATH).hasRole("PROVIDER")
                        .requestMatchers(HttpMethod.PUT, MEALS_BASE_PATH + "/{mealId}").hasRole("PROVIDER")
                        .requestMatchers(HttpMethod.DELETE, MEALS_BASE_PATH + "/{mealId}").hasRole("PROVIDER")
                        // Public catalog browsing endpoints — no authentication required
                        .requestMatchers(HttpMethod.GET, MEALS_BASE_PATH + "/provider/{providerId}").permitAll()
                        .requestMatchers(HttpMethod.GET, MEALS_BASE_PATH + "/type/{mealType}").permitAll()
                        .requestMatchers(HttpMethod.GET, MEALS_BASE_PATH + "/{mealId}").permitAll()
                        .requestMatchers(HttpMethod.GET, MEALS_BASE_PATH).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(headerAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
