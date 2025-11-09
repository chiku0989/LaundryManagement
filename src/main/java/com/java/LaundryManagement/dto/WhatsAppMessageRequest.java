package com.java.LaundryManagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.sql.Template;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WhatsAppMessageRequest {
    @JsonProperty("messaging_product")
    private String messagingProduct = "whatsapp";

    private String to;

    private String type = "template";

    private Template template;
}