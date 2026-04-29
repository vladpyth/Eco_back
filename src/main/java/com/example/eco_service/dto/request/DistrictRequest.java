package com.example.eco_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для сущности Cities")
public class DistrictRequest {


    @Schema(description = "Район", example = "Центральный")
    @NotBlank(message = "Район обязателен")
    @Size(max = 50, message = "Максимум 50 символов")
    private String name_district;


}
