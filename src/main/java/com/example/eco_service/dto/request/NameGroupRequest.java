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
@Schema(description = "Запрос для сущности NameGroup")
public class NameGroupRequest {

    @Schema(description = "Название группы", example = "Группа А")
    @NotBlank(message = "nameGroup обязателен")
    @Size(max = 150, message = "Максимум 150 символов")
    private String nameGroup;
}
