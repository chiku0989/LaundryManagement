package com.java.LaundryManagement.dto;

import com.java.LaundryManagement.models.WashStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWashRequestStatusDTO {
    @NotNull(message = "Request Id is required")
    private Long id;
    @NotNull(message = "Wash Status is Required")
    private WashStatus status;
}
