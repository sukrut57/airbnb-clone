package com.airbnb.clone.backend.infrastructure.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${keycloak-client.client-id}")
    private String keycloakClientId;

    // -----------------------------
    // Actuator & Swagger Filter Chain
    // -----------------------------

    @Configuration
    @Order(1)
    public static class ActuatorSecurityConfiguration{


        @Bean
        public SecurityFilterChain actuatorSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
            return httpSecurity
                    .securityMatcher("/actuator/**") // Limit this filter chain to /actuator/**
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(withDefaults())
                    .authorizeHttpRequests(req -> req.anyRequest().permitAll()) // allow all actuator requests
                    .build();
        }

        @Bean
        public WebSecurityCustomizer webSecurityCustomizer(){
            return web -> web.ignoring()
                    .requestMatchers("/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/webjars/**");
        }
    }

    // -----------------------------
    // API Filter Chain (Secured)
    // -----------------------------

    @Configuration
    @Order(2)
    public static class KeycloakSecurityConfiguration{}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
       return httpSecurity
                .securityMatcher("/api/**") // Only matches API endpoints
                .csrf(AbstractHttpConfigurer :: disable)
                .cors(withDefaults())
                .authorizeHttpRequests(
                        req ->
                                req.anyRequest().authenticated())
               .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(STATELESS))
               .oauth2ResourceServer(auth ->
                       auth.jwt(jwt -> jwt.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConvertor(keycloakClientId))))
               .build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("http://localhost:4200")); // ❗ Replace with your production domain later
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCEPT ,HttpHeaders.ORIGIN));
        corsConfig.setAllowCredentials(true); // Optional: Only allow if using cookies/auth headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
}

