package com.java.LaundryManagement.services;


import com.java.LaundryManagement.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class WhatsAppService {

    private static final Logger logger = LoggerFactory.getLogger(WhatsAppService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${whatsapp.api.url}")
    private String apiUrl;

    @Value("${whatsapp.api.phone-number-id}")
    private String phoneNumberId;

    @Value("${whatsapp.api.token}")
    private String apiToken;

    /**
     * Sends a WhatsApp message using a pre-approved template.
     *
     * @param to The recipient's phone number (with country code, e.g., "9199999XXXXX")
     * @param templateName The name of your approved template (e.g., "order_confirmation")
     * @param languageCode The language code (e.g., "en_US" or "en")
     * @param bodyParams A list of strings to fill the 'body' placeholders (e.g., {{1}}, {{2}})
     */
    public void sendTemplateMessage(String to, String templateName, String languageCode, List<String> bodyParams) {

        // 1. Construct the API URL
        String url = apiUrl + "/" + phoneNumberId + "/messages";

        // 2. Create Parameter objects from the simple list of strings
        List<Parameter> parameters = bodyParams.stream()
                .map(param -> new Parameter("text", param))
                .collect(Collectors.toList());

        // 3. Create the Component (we assume a simple 'body' component)
        Component bodyComponent = new Component("body", parameters);

        // 4. Create the Template object
        Template template = new Template(templateName, new Language(languageCode), List.of(bodyComponent));

        // 5. Create the main Request Body
        WhatsAppMessageRequest requestBody = new WhatsAppMessageRequest(to, template);

        // 6. Set up HTTP Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiToken);

        // 7. Create the HTTP Entity
        HttpEntity<WhatsAppMessageRequest> entity = new HttpEntity<>(requestBody, headers);

        // 8. Send the Request
        try {
            logger.info("Sending WhatsApp template '{}' to: {}", templateName, to);
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );
            logger.info("WhatsApp message sent successfully. Response: {}", response.getBody());
        } catch (HttpClientErrorException e) {
            // Handle API errors
            logger.error("Error sending WhatsApp message: {}", e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            // Handle other exceptions
            logger.error("An unexpected error occurred: {}", e.getMessage(), e);
        }
    }
}