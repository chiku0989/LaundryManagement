package com.java.LaundryManagement.dto;

import jakarta.validation.constraints.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class WashRequestRegistrationDTO {

    @NotEmpty(message = "Student Registration Number is Required")
    private String studentRegistrationNumber;

    @NotNull(message = "Cloth Count is required")
    @Positive(message = "Cloth Count Must be positive")
    @Min(value = 1, message = "Cloth count must be at least 1")
    @Max(value = 25, message = "Cloth count cannot be more than 25")
    private int clothCount;


}
