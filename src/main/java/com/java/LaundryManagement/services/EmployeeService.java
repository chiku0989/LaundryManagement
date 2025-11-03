package com.java.LaundryManagement.services;

import com.java.LaundryManagement.dto.EmployeeDTO;
import com.java.LaundryManagement.dto.EmployeeRegistationDTO;
import com.java.LaundryManagement.dto.UpdateEmployeeDTO;
import com.java.LaundryManagement.exception.InvalidRequest;
import com.java.LaundryManagement.exception.ResourceNotFoundException;
import com.java.LaundryManagement.exception.UserAlreadyExistsException;
import com.java.LaundryManagement.models.Employee;
import com.java.LaundryManagement.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public List<EmployeeDTO> getALlEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee employee : employees) {
            employeeDTOS.add(EmployeeDTO.mapEmployeeDTO(employee));
        }

        return employeeDTOS;
    }

    public EmployeeDTO createEmployee(EmployeeRegistationDTO employeeRegistationDTO) throws UserAlreadyExistsException {
        if(employeeRepository.existsById(employeeRegistationDTO.getUsername())){
            throw new UserAlreadyExistsException("User Already Exists");
        }
        Employee employee = new Employee();
        employee.setUsername(employeeRegistationDTO.getUsername());
        employee.setFirstName(employeeRegistationDTO.getFirstName());
        employee.setLastName(employeeRegistationDTO.getLastName());
        String hashedPassword = passwordEncoder.encode(employeeRegistationDTO.getPassword());
        employee.setPassword(hashedPassword);
        employee.setRole(employeeRegistationDTO.getRole());
        employee = employeeRepository.save(employee);



        return EmployeeDTO.mapEmployeeDTO(employee);
    }

    public EmployeeDTO getEmployeeById(String username) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                "Employee not found with username: " + username
        ));

        return EmployeeDTO.mapEmployeeDTO(employee);
    }

    public EmployeeDTO upsertEmployee(String username, UpdateEmployeeDTO updateEmployeeDTO)throws InvalidRequest,ResourceNotFoundException {


        if(!username.equals(updateEmployeeDTO.getUsername())){
            throw new InvalidRequest("Path variable username ('" + username +
                    "') does not match request body username ('" + updateEmployeeDTO.getUsername() + "').");
        }

        Employee employee = employeeRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with username: " + username
                ));
        employee.setFirstName(updateEmployeeDTO.getFirstName());
        employee.setLastName(updateEmployeeDTO.getLastName());
        employee.setRole(updateEmployeeDTO.getRole());
        employee = employeeRepository.save(employee);
        return EmployeeDTO.mapEmployeeDTO(employee);
    }
    public EmployeeDTO deleteEmployee(String username) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with username: " + username
                ));
        employeeRepository.delete(employee);
        return EmployeeDTO.mapEmployeeDTO(employee);
    }
}
