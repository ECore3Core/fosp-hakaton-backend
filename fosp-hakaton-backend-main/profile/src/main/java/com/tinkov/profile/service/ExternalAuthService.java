package com.tinkov.profile.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ExternalAuthService {

    @Value("${authentication.server.url}")
    private String authServerUrl;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public ExternalAuthService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public boolean validateToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    authServerUrl + "/authentication/is-token-valid",
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                IsValidTokenResponse authResponse = objectMapper.readValue(response.getBody(), IsValidTokenResponse.class);
                return authResponse.getValid();
            } else {
                throw new RuntimeException("Invalid token");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to validate token", e);
        }
    }

    public Long getUserId(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    authServerUrl + "/user/getIdByToken",
                    HttpMethod.GET,
                    entity,
                    String.class
            ); 
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                UserIdResponse userIdResponse = objectMapper.readValue(response.getBody(), UserIdResponse.class);
                return userIdResponse.getId(); 
            } else {
                throw new RuntimeException("Failed to retrieve user ID: unexpected response status");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user ID", e);
        }
    }

    public static class UserIdResponse {

        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    public static class IsValidTokenResponse {

        private boolean valid;

        public boolean getValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }
    }
}
