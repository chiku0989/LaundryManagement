package com.java.LaundryManagement.services;

import com.java.LaundryManagement.dto.LoginRequestDTO;
import com.java.LaundryManagement.dto.LoginResponseDTO;
import com.java.LaundryManagement.models.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jWTService;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()
                )
        );

        Employee employee = (Employee) authentication.getPrincipal();

        String token = jWTService.generateToken(employee);

        return new LoginResponseDTO(token);
    }
}
