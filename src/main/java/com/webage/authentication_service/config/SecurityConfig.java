package com.webage.authentication_service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         return http
             //  Disable sessions.  We want a stateless application:
             .sessionManagement(
                 session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

             //  CSRF protection is merely extra overhead with session management disabled:
             .csrf(csrf -> csrf.disable())

             //  All inbound requests must be authenticated:
             .authorizeHttpRequests( auth -> auth
                 .requestMatchers("/account/register", "/account/token").permitAll()
                 .anyRequest().authenticated()
             )
             .httpBasic(Customizer.withDefaults())

             .build();
     }

    @Bean
    public InMemoryUserDetailsManager users() {
        return new InMemoryUserDetailsManager(
            User.withUsername("client")
                .password("{noop}password")
                .authorities("write")
                .build()
        );
    }
}

