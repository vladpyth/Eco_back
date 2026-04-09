package com.example.eco_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Добавление/связь AroundBuild с объектом")
public class ObjectAroundBuildLinkRequest {
    @Schema(description = "ID существующей записи AroundBuild", example = "1")
    private Long aroundBuildId;

    @Schema(description = "Название для новой записи AroundBuild", example = "Жилой дом")
    @Size(max = 150, message = "Максимум 150 символов")
    private String name;
}
