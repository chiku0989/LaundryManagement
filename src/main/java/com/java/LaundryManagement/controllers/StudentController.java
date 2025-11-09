package com.java.LaundryManagement.controllers;


import com.java.LaundryManagement.dto.StudentDTO;
import com.java.LaundryManagement.dto.StudentRegistrationDTO;
import com.java.LaundryManagement.dto.UpdateStudentDTO;
import com.java.LaundryManagement.services.StudentServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentServices studentServices;

    @GetMapping("/")
    ResponseEntity<List<StudentDTO> > getStudents(){
        List<StudentDTO> studentDTOs = studentServices.getAllStudents();
        return ResponseEntity.status(HttpStatus.OK).body(studentDTOs);
    }

    @PostMapping("/")
    ResponseEntity<StudentDTO> saveStudent(@RequestBody @Valid StudentRegistrationDTO studentRegistrationDTO){
        StudentDTO studentDTO = studentServices.createStudent(studentRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentDTO);
    }

    @GetMapping("/{registrationNo}")
    ResponseEntity<StudentDTO> getStudent(@PathVariable String registrationNo){
        StudentDTO studentDTO = studentServices.getStudentById(registrationNo);

        return ResponseEntity.status(HttpStatus.OK).body(studentDTO);
    }

    @PutMapping("/{registrationNo}")
    ResponseEntity<StudentDTO> updateStudent(@PathVariable String registrationNo, @RequestBody @Valid UpdateStudentDTO updateStudentDTO){
        StudentDTO studentDTO = studentServices.updateStudent(registrationNo, updateStudentDTO);
        return ResponseEntity.status(HttpStatus.OK).body(studentDTO);
    }

    @DeleteMapping("/{registrationNo}")
    ResponseEntity<StudentDTO> deleteStudent(@PathVariable String registrationNo){
        StudentDTO studentDTO = studentServices.deleteStudent(registrationNo);
        return ResponseEntity.status(HttpStatus.OK).body(studentDTO);
    }
}
