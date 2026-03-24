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
@Schema(description = "Запрос для сущности TypeTrash1")
public class TypeTrash1Request {

    @Schema(description = "Название типа отходов", example = "Твёрдые коммунальные")
    @NotBlank(message = "nameTypeTrash1 обязателен")
    @Size(max = 150, message = "Максимум 150 символов")
    private String nameTypeTrash1;
}
