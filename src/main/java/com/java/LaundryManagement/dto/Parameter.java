package com.java.LaundryManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parameter {
    private String type; // e.g., "text", "currency", "date_time"
    private String text; // The value to insert, e.g., "Aditya"
}