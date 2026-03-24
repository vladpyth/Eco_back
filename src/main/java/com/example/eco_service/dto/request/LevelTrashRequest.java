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
@Schema(description = "Запрос для сущности LevelTrash")
public class LevelTrashRequest {

    @Schema(description = "Название уровня отходов", example = "Уровень I")
    @NotBlank(message = "nameLevelTrash обязателен")
    @Size(max = 150, message = "Максимум 150 символов")
    private String nameLevelTrash;
}
