package com.skillmentor_backend.final_project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CorsFilter corsFilter;
    private final JwtRoleConverter jwtRoleConverter; // <-- INJECT THE NEW CONVERTER

    @Value("${clerk.jwks-url}")
    private String jwksUrl;

    // Updated constructor to inject both beans
    public SecurityConfig(CorsFilter corsFilter, JwtRoleConverter jwtRoleConverter) {
        this.corsFilter = corsFilter;
        this.jwtRoleConverter = jwtRoleConverter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(corsFilter, CorsFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ROLE_admin")
                        .requestMatchers("/student/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/mentors/**").permitAll()
                        .anyRequest().authenticated()
                )
                // --- THIS IS THE FIX ---
                // Tell the resource server to use our custom converter
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt ->
                        jwt.jwtAuthenticationConverter(jwtRoleConverter)
                ));
        // --- END OF FIX ---

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwksUrl).build();
    }
}