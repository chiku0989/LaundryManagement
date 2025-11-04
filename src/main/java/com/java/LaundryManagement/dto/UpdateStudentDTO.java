package com.java.LaundryManagement.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStudentDTO {
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

    @NotNull(message = "Remaining washes must be specified")
    @Positive(message = "Remaining washes must be a positive number")
    private int remainingWashes;


    @AssertTrue(message = "Term end date must be after term start date")
    public boolean isTermDatesValid() {

        if (termStartDate == null || termEndDate == null) {
            return true;
        }

        return termEndDate.isAfter(termStartDate);
    }

    @AssertTrue(message = "remaining washes must be less than or equal to total washes")
    public boolean isRemainingWashesValid() {
        if (remainingWashes <= totalWashesAllocated) {
            return true;
        }
        return false;
    }

}
