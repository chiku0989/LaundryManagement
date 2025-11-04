package com.java.LaundryManagement.services;
import com.java.LaundryManagement.dto.UpdateWashRequestStatusDTO;
import com.java.LaundryManagement.dto.WashRequestDTO;
import com.java.LaundryManagement.dto.WashRequestRegistrationDTO;
import com.java.LaundryManagement.exception.InvalidRequest;
import com.java.LaundryManagement.exception.NotEnoughWashesException;
import com.java.LaundryManagement.exception.ResourceNotFoundException;
import com.java.LaundryManagement.models.Student;
import com.java.LaundryManagement.models.WashRequest;
import com.java.LaundryManagement.models.WashStatus;
import com.java.LaundryManagement.repositories.StudentRepository;
import com.java.LaundryManagement.repositories.WashRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WashRequestService {

    private final StudentRepository studentRepository;
    private final WashRequestRepository washRequestRepository;

    @Transactional(readOnly = true)
    public List<WashRequestDTO> getAllWashRequests() {
        List<WashRequest>  washRequests = washRequestRepository.findAll();
        List<WashRequestDTO> washRequestDTOS = new ArrayList<>();
        for(WashRequest washRequest : washRequests){
            washRequestDTOS.add(WashRequestDTO.toDTO(washRequest));
        }
        return washRequestDTOS;
    }

    @Transactional
    public WashRequestDTO createNewWashRequest(WashRequestRegistrationDTO washRequestRegistrationDTO)
    {
        String RegistrationNumber = washRequestRegistrationDTO.getStudentRegistrationNumber();
        Student student = studentRepository.findById(RegistrationNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with registration number : " + RegistrationNumber
                ));

        int remainingWashes = student.getRemainingWashes();
        if(remainingWashes < 1){
            throw new NotEnoughWashesException(RegistrationNumber + " do not have sufficient washes");
        }else{
            student.setRemainingWashes(remainingWashes-1);
            studentRepository.save(student);
        }

        WashRequest washRequest = new WashRequest();
        washRequest.setStudent(student);
        washRequest.setClothCount(washRequestRegistrationDTO.getClothCount());
        washRequest.setStatus(WashStatus.PENDING);

        washRequest = washRequestRepository.save(washRequest);


        return WashRequestDTO.toDTO(washRequest);
    }

    @Transactional(readOnly = true)
    public List<WashRequestDTO> getStudentWashRequests(String studentRegistrationNumber){
        List<WashRequest> washRequests = washRequestRepository.findByStudentRegistrationNo(studentRegistrationNumber);
        List<WashRequestDTO> washRequestDTOS = new ArrayList<>();
        for(WashRequest washRequest : washRequests){
            washRequestDTOS.add(WashRequestDTO.toDTO(washRequest));
        }
        return washRequestDTOS;
    }

    @Transactional(readOnly = true)
    public List<WashRequestDTO> getWashRequestsByStatus(WashStatus washStatus){
        List<WashRequest> washRequests = washRequestRepository.findByStatus(washStatus);
        List<WashRequestDTO> washRequestDTOS = new ArrayList<>();
        for(WashRequest washRequest : washRequests){
            washRequestDTOS.add(WashRequestDTO.toDTO(washRequest));
        }
        return washRequestDTOS;
    }
    @Transactional
    public WashRequestDTO DeleteWashRequest(long id){
        WashRequest washRequest = washRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Wash Request not found with ID " + id
                ));
        washRequestRepository.delete(washRequest);
        return WashRequestDTO.toDTO(washRequest);
    }

    @Transactional
    public WashRequestDTO UpdateWashRequest(UpdateWashRequestStatusDTO updateWashRequestStatusDTO, long id){
        long washRequestId = updateWashRequestStatusDTO.getId();
        WashRequest washRequest = washRequestRepository.findById(washRequestId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Wash Request not found with ID " + washRequestId
                ));

        if(id != washRequestId){
            throw new InvalidRequest("Path variable request Id ('" + id +
                    "') does not match request body request Id ('" + washRequestId + "').");
        }

        WashStatus washStatus = updateWashRequestStatusDTO.getStatus();

        if(washRequest.getStatus().equals(washStatus)){
           return WashRequestDTO.toDTO(washRequest);
        }
        if(washRequest.getStatus().equals(WashStatus.COMPLETED)){
            throw new InvalidRequest("Wash Request is already completed");
        }

        if(updateWashRequestStatusDTO.getStatus().equals(WashStatus.COMPLETED)){
            washRequest.setDatePickedUp(LocalDateTime.now());
        }
        washRequest.setStatus(updateWashRequestStatusDTO.getStatus());
        washRequest = washRequestRepository.save(washRequest);

        return WashRequestDTO.toDTO(washRequest);
    }
}
