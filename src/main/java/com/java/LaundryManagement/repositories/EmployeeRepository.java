package com.java.LaundryManagement.repositories;

import com.java.LaundryManagement.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
