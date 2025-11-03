package com.java.LaundryManagement.configuration;

import com.java.LaundryManagement.filters.RequestLoggingFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final RequestLoggingFilter requestLoggingFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                )


                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/employees").hasRole("ADMIN")
                        .requestMatchers("/employees/**").hasRole("ADMIN")
                        .requestMatchers("/**").hasAnyRole("ADMIN", "STAFF")
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(requestLoggingFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
