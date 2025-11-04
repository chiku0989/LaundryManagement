package com.java.LaundryManagement.controllers;

import com.java.LaundryManagement.dto.UpdateWashRequestStatusDTO;
import com.java.LaundryManagement.dto.WashRequestDTO;
import com.java.LaundryManagement.dto.WashRequestRegistrationDTO;
import com.java.LaundryManagement.models.WashStatus;
import com.java.LaundryManagement.services.WashRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wash-requests")
@RequiredArgsConstructor
public class WashRequestController {

    private final WashRequestService washRequestService;

    @GetMapping("/")
    public ResponseEntity<List<WashRequestDTO>> getAllWashRequests() {
        List<WashRequestDTO> washRequestDTOS = washRequestService.getAllWashRequests();
        return ResponseEntity.status(HttpStatus.OK).body(washRequestDTOS);
    }

    @PostMapping("/")
    public ResponseEntity<WashRequestDTO> createWashRequest(@RequestBody @Valid WashRequestRegistrationDTO washRequestRegistrationDTO) {
        WashRequestDTO washRequestDTO = washRequestService.createNewWashRequest(washRequestRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(washRequestDTO);
    }

    @GetMapping("/{studentRegistrationNumber}")
    public ResponseEntity<List<WashRequestDTO>> getWashRequestByStudenRegistationNumber(@PathVariable String studentRegistrationNumber) {
        List<WashRequestDTO> washRequestDTOS = washRequestService.getStudentWashRequests(studentRegistrationNumber);
        return ResponseEntity.status(HttpStatus.OK).body(washRequestDTOS);
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<WashRequestDTO>> getWashRequestByStudenRegistationNumber(@PathVariable WashStatus status) {
        List<WashRequestDTO> washRequestDTOS = washRequestService.getWashRequestsByStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(washRequestDTOS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WashRequestDTO> updateWashRequestStatus(@RequestBody @Valid UpdateWashRequestStatusDTO updateWashRequestStatusDTO, @PathVariable long id) {
        WashRequestDTO washRequestDTO = washRequestService.UpdateWashRequest(updateWashRequestStatusDTO,id);

        return ResponseEntity.status(HttpStatus.CREATED).body(washRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WashRequestDTO> deleteById(@PathVariable long id) {
        WashRequestDTO washRequestDTO = washRequestService.DeleteWashRequest(id);

        return ResponseEntity.status(HttpStatus.OK).body(washRequestDTO);
    }
}
