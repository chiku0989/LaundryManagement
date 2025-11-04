package com.java.LaundryManagement.dto;


import com.java.LaundryManagement.models.WashRequest;
import com.java.LaundryManagement.models.WashStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WashRequestDTO {
    private Long id;
    private WashStatus status;
    private LocalDateTime dateRequested;
    private LocalDateTime datePickedUp;
    private String Student_registration_number;


    public static WashRequestDTO toDTO(WashRequest washRequest) {
        WashRequestDTO washRequestDTO = new WashRequestDTO();
        washRequestDTO.setId(washRequest.getId());
        washRequestDTO.setStatus(washRequest.getStatus());
        washRequestDTO.setDateRequested(washRequest.getDateRequested());
        washRequestDTO.setDatePickedUp(washRequest.getDatePickedUp());
        washRequestDTO.setStudent_registration_number(washRequest.getStudent().getRegistrationNo());

        return washRequestDTO;
    }
}
