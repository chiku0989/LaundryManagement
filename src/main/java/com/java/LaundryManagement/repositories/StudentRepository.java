package com.java.LaundryManagement.repositories;

import com.java.LaundryManagement.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}
