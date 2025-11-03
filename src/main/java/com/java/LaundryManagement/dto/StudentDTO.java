package com.java.LaundryManagement.dto;

import com.java.LaundryManagement.models.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {


    private String registrationNo;

    private String fullName;

    private String phoneNumber;

    private String roomNumber;

    private LocalDate termStartDate;

    private LocalDate termEndDate;


    private int totalWashesAllocated;


    private int remainingWashes;

    public static StudentDTO studentDTO(Student student){
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setRegistrationNo(student.getRegistrationNo());
        studentDTO.setFullName(student.getFullName());
        studentDTO.setPhoneNumber(student.getPhoneNumber());
        studentDTO.setRoomNumber(student.getRoomNumber());
        studentDTO.setTermStartDate(student.getTermStartDate());
        studentDTO.setTermEndDate(student.getTermEndDate());
        studentDTO.setTotalWashesAllocated(student.getTotalWashesAllocated());
        studentDTO.setRemainingWashes(student.getRemainingWashes());

        return studentDTO;
    }
}
