package com.example.eco_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для сущности NatualSaveBuilding")
public class NatualSaveBuildingRequest {

    @Schema(description = "Название", example = "Здание охраны природы")
    @Size(max = 255, message = "Максимум 255 символов")
    private String name;
}
