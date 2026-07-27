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
@Schema(description = "Запрос для сущности StorageScheme")
public class StorageSchemeRequest {

    @Schema(description = "Название схемы хранения", example = "Наземное складирование")
    @NotBlank(message = "nameStorageScheme обязателен")
    @Size(max = 255, message = "Максимум 255 символов")
    private String nameStorageScheme;
}
