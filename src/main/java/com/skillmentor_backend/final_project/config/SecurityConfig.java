package com.skillmentor_backend.final_project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter; // Import CorsFilter

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CorsFilter corsFilter; // Inject your CorsFilter bean

    @Value("${clerk.jwks-url}")
    private String jwksUrl;

    // Constructor to inject the bean
    public SecurityConfig(CorsFilter corsFilter) {
        this.corsFilter = corsFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Add the CorsFilter before the main security filter
                .addFilterBefore(corsFilter, CorsFilter.class)
                // Disable CSRF protection, as we are using token-based auth
                .csrf(csrf -> csrf.disable())
                // Set session management to stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Secure admin endpoints
                        .requestMatchers("/api/admin/**").hasAuthority("ROLE_admin")
                        // Secure student-specific endpoints
                        .requestMatchers("/api/student/**").authenticated()
                        // Allow public access to view mentors and classes
                        .requestMatchers(HttpMethod.GET, "/api/mentors/**", "/api/classrooms/**").permitAll()
                        // Any other request must be authenticated
                        .anyRequest().authenticated()
                )
                // Configure the app as an OAuth2 Resource Server for JWTs
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder())));

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // The decoder will use the JWKS URL to fetch public keys for verifying token signatures
        return NimbusJwtDecoder.withJwkSetUri(jwksUrl).build();
    }
}