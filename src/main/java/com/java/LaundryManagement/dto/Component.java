package com.java.LaundryManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Component {
    // This tells API which block we are filling
    private String type; // e.g., "body", "header", "button"

    // This holds the list of parameters for that block
    private List<Parameter> parameters;
}