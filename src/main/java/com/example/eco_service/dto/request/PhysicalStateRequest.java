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
@Schema(description = "Запрос для сущности PhysicalState")
public class PhysicalStateRequest {

    @Schema(description = "Состояние", example = "твёрдое")
    @NotBlank(message = "state обязателен")
    @Size(max = 22, message = "Максимум 22 символа")
    private String state;
}
