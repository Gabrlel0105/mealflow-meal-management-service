package com.gabrielmamani.mealmanagement.shared.infrastructure.security.configuration;

import com.gabrielmamani.mealmanagement.shared.infrastructure.security.authorization.HeaderAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfiguration {

    @Value("${spring.security.user.name:admin}")
    private String swaggerUser;

    @Value("${spring.security.user.password:Betito123}")
    private String swaggerPassword;

    private static final String[] SWAGGER_PATHS = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml"
    };

    private static final String[] PUBLIC_DOCUMENTATION_PATHS = {
            "/actuator/**"
    };

    private static final String MEALS_BASE_PATH = "/api/v1/meals";

    @Value("${cors.allowed-origins:http://localhost:4173,http://localhost:*,https://mealflow-745df.web.app,https://mealflow-745df.firebaseapp.com}")
    private String[] allowedOrigins;

    @Bean
    @Order(1)
    public SecurityFilterChain swaggerFilterChain(HttpSecurity http) throws Exception {
        var userDetailsService = new InMemoryUserDetailsManager(
                User.withUsername(swaggerUser)
                        .password("{noop}" + swaggerPassword)
                        .roles("SWAGGER")
                        .build()
        );
        http
                .securityMatcher(SWAGGER_PATHS)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .userDetailsService(userDetailsService);
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, HeaderAuthenticationFilter headerAuthenticationFilter) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PUBLIC_DOCUMENTATION_PATHS).permitAll()
                        // Allow image uploads
                        .requestMatchers(HttpMethod.POST, MEALS_BASE_PATH + "/upload-image").permitAll()
                        // Provider & Admin management endpoints
                        .requestMatchers(HttpMethod.GET, MEALS_BASE_PATH + "/provider").hasAnyRole("PROVIDER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, MEALS_BASE_PATH).hasAnyRole("PROVIDER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, MEALS_BASE_PATH + "/{mealId}").hasAnyRole("PROVIDER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, MEALS_BASE_PATH + "/{mealId}").hasAnyRole("PROVIDER", "ADMIN")
                        // Public catalog browsing endpoints — no authentication required
                        .requestMatchers(HttpMethod.GET, MEALS_BASE_PATH + "/provider/{providerId}").permitAll()
                        .requestMatchers(HttpMethod.GET, MEALS_BASE_PATH + "/type/{mealType}").permitAll()
                        .requestMatchers(HttpMethod.GET, MEALS_BASE_PATH + "/{mealId}").permitAll()
                        .requestMatchers(HttpMethod.GET, MEALS_BASE_PATH).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(headerAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList(allowedOrigins));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
