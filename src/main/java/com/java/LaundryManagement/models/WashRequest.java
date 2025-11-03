package com.java.LaundryManagement.models;


import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "wash_Requests")
public class WashRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WashStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateRequested;
    private LocalDateTime datePickedUp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "student_registration_no",
            referencedColumnName = "registrationNo"
    )
    private Student student;

}
