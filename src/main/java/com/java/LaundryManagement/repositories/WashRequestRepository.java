package com.java.LaundryManagement.repositories;

import com.java.LaundryManagement.models.WashRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WashRequestRepository extends JpaRepository<WashRequest, Long> {
}
