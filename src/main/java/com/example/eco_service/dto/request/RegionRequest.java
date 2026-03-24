package com.example.eco_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для сущности Region")
public class RegionRequest {

    @Schema(description = "Название региона", example = "Московская область")
    @NotBlank(message = "nameRegion обязателен")
    @Size(max = 50, message = "Максимум 50 символов")
    private String nameRegion;
}
