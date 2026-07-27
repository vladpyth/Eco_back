package com.example.eco_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для сущности AroundBuild")
public class AroundBuildRequest {

    @Schema(description = "Название", example = "Зона А")
    @NotEmpty()
    @Size(max = 255, message = "Максимум 255 символов")
    private String name;
}
