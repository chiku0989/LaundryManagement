package com.java.LaundryManagement.services;

import com.java.LaundryManagement.dto.Language;
import com.java.LaundryManagement.dto.StudentDTO;
import com.java.LaundryManagement.dto.StudentRegistrationDTO;
import com.java.LaundryManagement.dto.UpdateStudentDTO;
import com.java.LaundryManagement.exception.InvalidRequest;
import com.java.LaundryManagement.exception.ResourceNotFoundException;
import com.java.LaundryManagement.exception.UserAlreadyExistsException;

import com.java.LaundryManagement.models.Student;
import com.java.LaundryManagement.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServices {

    private final StudentRepository studentRepository;
    private final WhatsAppService whatsAppService;
    @Transactional(readOnly = true)
    public List<StudentDTO> getAllStudents(){
        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentDTOS = new ArrayList<>();

        for(Student student : students){
            studentDTOS.add(StudentDTO.studentDTO(student));
        }

        return studentDTOS;
    }
    @Transactional(readOnly = true)
    public StudentDTO getStudentById(String registrationNo){
        Student student = studentRepository.findById(registrationNo)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with registration number : " + registrationNo
                ));
        return StudentDTO.studentDTO(student);
    }

    @Transactional
    public StudentDTO createStudent(StudentRegistrationDTO studentRegistrationDTO){
        if(studentRepository.existsById(studentRegistrationDTO.getRegistrationNo())){
            throw new UserAlreadyExistsException("Student with username " + studentRegistrationDTO.getRegistrationNo() + " already exists");
        }

        Student student = new Student();
        student.setRegistrationNo(studentRegistrationDTO.getRegistrationNo());
        student.setFullName(studentRegistrationDTO.getFullName());
        student.setPhoneNumber(studentRegistrationDTO.getPhoneNumber());
        student.setRoomNumber(studentRegistrationDTO.getRoomNumber());
        student.setTermStartDate(studentRegistrationDTO.getTermStartDate());
        student.setTermEndDate(studentRegistrationDTO.getTermEndDate());
        student.setTotalWashesAllocated(studentRegistrationDTO.getTotalWashesAllocated());
        student.setRemainingWashes(studentRegistrationDTO.getTotalWashesAllocated());
        student = studentRepository.save(student);

        String PhoneNumber = "91"+studentRegistrationDTO.getPhoneNumber();
        String templateName = "welcome_student";
        String languageCode = "en";

        List<String> Params = List.of(studentRegistrationDTO.getFullName(), studentRegistrationDTO.getRoomNumber(), studentRegistrationDTO.getRoomNumber(), ""+studentRegistrationDTO.getTotalWashesAllocated());


        whatsAppService.sendTemplateMessage(PhoneNumber, templateName, languageCode, Params);
        return StudentDTO.studentDTO(student);
    }

    @Transactional
    public StudentDTO updateStudent(String registrationNo, UpdateStudentDTO updateStudentDTO){

        if(!registrationNo.equals(updateStudentDTO.getRegistrationNo())){
           throw new InvalidRequest("Path variable registration number ("+ registrationNo +") does not match request body registration number ("+ updateStudentDTO.getRegistrationNo() +")");
        }

        Student student = studentRepository.findById(registrationNo)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with registration number : " + registrationNo
                ));

        student.setFullName(updateStudentDTO.getFullName());
        student.setPhoneNumber(updateStudentDTO.getPhoneNumber());
        student.setRoomNumber(updateStudentDTO.getRoomNumber());
        student.setTermStartDate(updateStudentDTO.getTermStartDate());
        student.setTermEndDate(updateStudentDTO.getTermEndDate());
        student.setTotalWashesAllocated(updateStudentDTO.getTotalWashesAllocated());
        student.setRemainingWashes(updateStudentDTO.getRemainingWashes());
        student = studentRepository.save(student);

        return StudentDTO.studentDTO(student);
    }

    @Transactional
    public StudentDTO deleteStudent(String registrationNo){
        Student student = studentRepository.findById(registrationNo)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with registration number : " + registrationNo
                ));
        studentRepository.delete(student);
        return StudentDTO.studentDTO(student);
    }

}
