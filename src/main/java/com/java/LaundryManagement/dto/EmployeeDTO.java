package com.java.LaundryManagement.dto;

import com.java.LaundryManagement.models.Employee;
import com.java.LaundryManagement.models.EmployeeRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Role must be specified")
    private EmployeeRole role;

    @NotNull(message = "createdAt must be specified")
    private LocalDateTime createdAt;

    public static EmployeeDTO mapEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setUsername(employee.getUsername());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setRole(employee.getRole());
        employeeDTO.setCreatedAt(employee.getCreatedAt());

        return employeeDTO;
    }
}
