package com.example.eco_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для сущности GroupPlaceSave")
public class GroupPlaceSaveRequest {

    @Schema(description = "Название региона", example = "Северо-Запад")
    @NotBlank(message = "nameRegion обязателен")
    private String nameRegion;
}
