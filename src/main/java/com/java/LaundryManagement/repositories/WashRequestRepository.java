package com.java.LaundryManagement.repositories;

import com.java.LaundryManagement.models.WashRequest;
import com.java.LaundryManagement.models.WashStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WashRequestRepository extends JpaRepository<WashRequest, Long> {

    List<WashRequest> findByStudentRegistrationNo(String registrationNo);

    List<WashRequest> findByStatus(WashStatus status);
}
