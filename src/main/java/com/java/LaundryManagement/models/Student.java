package com.java.LaundryManagement.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


public class Student {

    @Id
    private String registrationNo;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String roomNumber;
    @Column(nullable = false)
    private LocalDate termStartDate;
    @Column(nullable = false)
    private LocalDate termEndDate;

    @Column(nullable = false)
    private int totalWashesAllocated;

    @Column(nullable = false)
    private int remainingWashes;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<WashRequest> washRequest;
}
