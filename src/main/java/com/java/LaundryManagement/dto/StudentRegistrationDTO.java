package com.java.LaundryManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.Data;

@Data
public class StudentRegistrationDTO {

    @NotBlank(message = "Registration number is required")
    private String registrationNo;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Room number is required")
    private String roomNumber;

    @NotNull(message = "Term start date is required")
    private LocalDate termStartDate;

    @NotNull(message = "Term end date is required")
    private LocalDate termEndDate;

    @NotNull(message = "Total washes must be specified")
    @Positive(message = "Total washes must be a positive number")
    private int totalWashesAllocated;



}