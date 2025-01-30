package com.tinkov.profile.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {
    
    private String companyName;

    private Long userId;

    private String description;

    private String website;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
