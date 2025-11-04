package com.java.LaundryManagement.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

import lombok.Data;

@Data
public class StudentRegistrationDTO {

    @NotBlank(message = "Registration number is required")
    private String registrationNo;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
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


    @AssertTrue(message = "Term end date must be after term start date")
    public boolean isTermDatesValid() {

        if (termStartDate == null || termEndDate == null) {
            return true;
        }

        return termEndDate.isAfter(termStartDate);
    }

}