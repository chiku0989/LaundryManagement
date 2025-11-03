package com.java.LaundryManagement.controllers;

import com.java.LaundryManagement.dto.EmployeeDTO;
import com.java.LaundryManagement.dto.EmployeeRegistationDTO;

import com.java.LaundryManagement.dto.UpdateEmployeeDTO;
import com.java.LaundryManagement.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/")
    public ResponseEntity<List<EmployeeDTO>> getEmployees() {
        List<EmployeeDTO> employeeDTOS = employeeService.getALlEmployee();
        return ResponseEntity.status(HttpStatus.OK).body(employeeDTOS);
    }

    @PostMapping("/")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeRegistationDTO employeeRegistationDTO) {
        EmployeeDTO employeeDTO = employeeService.createEmployee(employeeRegistationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeDTO);
    }

    @GetMapping("/{username}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable String username) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(username);
        return ResponseEntity.status(HttpStatus.OK).body(employeeDTO);
    }

    @PutMapping("/{username}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody @Valid UpdateEmployeeDTO updateEmployeeDTO, @PathVariable String username) {
        EmployeeDTO employeeDTO = employeeService.upsertEmployee(username, updateEmployeeDTO);
        return ResponseEntity.status(HttpStatus.OK).body(employeeDTO);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<EmployeeDTO> deleteEmployee(@PathVariable String username) {
        EmployeeDTO employeeDTO = employeeService.deleteEmployee(username);
        return ResponseEntity.status(HttpStatus.OK).body(employeeDTO);
    }

}
