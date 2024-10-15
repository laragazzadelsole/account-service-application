package com.webage.authentication_service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/account/register", "/account/token").permitAll() // Allow login without authentication
                .anyRequest().authenticated() // Require authentication for other requests
            )
            .csrf(csrf -> csrf.disable()); // Disable CSRF protection if necessary

        return http.build();
    }
}
