package com.tinkov.profile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequest {
    
    @Size(min = 2, max = 50, message = "Название компании должно содержить от 2 до 50 символов")
    @NotBlank(message = "Название комапнии не может быть пустым")
    private String companyName;

    @NotBlank(message = "Id не может быть пустым")
    private Long userId;

    @Size(min = 10, max = 500, message = "Описание компани должно содержать от 10 до 500 символов")
    @NotBlank(message = "Описание компани не может быть пустым")
    private String description;

    @Size(min = 2, max = 60, message = "Ссылка на сайт должна содержать от 2 до 60 символов")
    private String website;
}
