package com.java.LaundryManagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class WhatsAppMessageRequest {
    @JsonProperty("messaging_product")
    private String messagingProduct = "whatsapp";

    private String to;

    private String type = "template";

    private Template template;

    public WhatsAppMessageRequest(String to, Template template) {
        this.to = to;
        this.template = template;
    }

}