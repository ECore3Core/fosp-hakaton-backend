package com.tinkov.profile.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequest {
    
    @Size(min = 2, max = 50, message = "Название услуги должно содержить от 2 до 50 символов")
    @NotBlank(message = "Название комапнии не может быть пустым")
    private String serviceName;

    @Size(min = 10, max = 500, message = "Описание компани должно содержать от 10 до 500 символов")
    @NotBlank(message = "Описание компани не может быть пустым")
    private String description;

    @NotBlank(message = "Цена услуги не может быть пустой")
    private BigDecimal price;
}
