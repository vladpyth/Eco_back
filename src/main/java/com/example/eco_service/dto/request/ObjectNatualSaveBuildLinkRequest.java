package com.example.eco_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Добавление/связь NatualSaveBuilding с объектом")
public class ObjectNatualSaveBuildLinkRequest {
    @Schema(description = "ID существующей записи NatualSaveBuilding", example = "1")
    private Long natualSaveBuildId;

    @Schema(description = "Название для новой записи NatualSaveBuilding", example = "Обваловка")
    @Size(max = 255, message = "Максимум 255 символов")
    private String name;
}
