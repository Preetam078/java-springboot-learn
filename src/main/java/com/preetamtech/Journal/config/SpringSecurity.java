package com.preetamtech.Journal.config;

import com.preetamtech.Journal.repositories.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; // Import DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    // Define a SecurityFilterChain bean for configuring HTTP security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Only requests to /journal/** require authentication
                        .requestMatchers("/journal/**", "/user/**").authenticated()
                        .anyRequest().permitAll() // All other requests (including /user/**) are permitted without authentication
                )
                .httpBasic();
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }

    // Define a PasswordEncoder bean for password hashing
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for strong password hashing
    }

    // Define a DaoAuthenticationProvider bean to integrate UserDetailsService and PasswordEncoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // Set your custom UserDetailsService
        authProvider.setPasswordEncoder(passwordEncoder());     // Set your PasswordEncoder
        return authProvider;
    }
}