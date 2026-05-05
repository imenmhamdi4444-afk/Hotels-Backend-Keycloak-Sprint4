package com.imen.hotels.security;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    KeycloakRoleConverter keycloakRoleConverter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
                @Override
                public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setExposedHeaders(Collections.singletonList("Authorization"));
                    return config;
                }
            }))
            .authorizeHttpRequests(requests -> requests
                // Consulter les hôtels et les types : ADMIN et USER
                .requestMatchers(HttpMethod.GET, "/api/hotels/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/api/type-hotel/**").hasAnyAuthority("ADMIN", "USER")
                
                // Modifier les hôtels et types : ADMIN seulement
                .requestMatchers(HttpMethod.POST, "/api/hotels/**").hasAuthority("ADMIN")
                .requestMatchers("/api/getImage/**").permitAll()
                .requestMatchers("/api/image/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/hotels/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/hotels/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/type-hotel/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/uploadImage/**").hasAuthority("ADMIN")
                
                .anyRequest().authenticated())
            .oauth2ResourceServer(rs -> rs.jwt(jwt -> jwt.jwtAuthenticationConverter(keycloakRoleConverter)));

        return http.build();
    }
}
