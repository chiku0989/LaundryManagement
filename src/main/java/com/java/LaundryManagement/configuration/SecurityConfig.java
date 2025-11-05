package com.java.LaundryManagement.configuration;

import com.java.LaundryManagement.exception.ResourceNotFoundException;
import com.java.LaundryManagement.filters.JWTFilter;
import com.java.LaundryManagement.filters.RequestLoggingFilter;

import com.java.LaundryManagement.repositories.EmployeeRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthEntryPoint customAuthEntryPoint;
    private final RequestLoggingFilter requestLoggingFilter;
    private final JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(customAuthEntryPoint) // <-- 2. Use it here
                )


                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/students").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers("/students/**").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers("/employees").hasRole("ADMIN")
                        .requestMatchers("/employees/**").hasRole("ADMIN")
                        .requestMatchers("/**").hasAnyRole("STAFF", "ADMIN")
                        .anyRequest().authenticated()
                );


        http.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(requestLoggingFilter, JWTFilter.class);
        return http.build();
    }
}
