package com.example.eco_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для сущности Cities")
public class CitiesRequest {

    @Schema(description = "ID региона", example = "1")
    @NotNull(message = "idRegion обязателен")
    private Long idRegion;

    @Schema(description = "Индекс", example = "123456")
    @NotBlank(message = "Индекс обязателен")
    @Size(min = 1, max = 7, message = "Индекс от 1 до 7 символов")
    private String index;

    @Schema(description = "Район", example = "Центральный")
    @NotBlank(message = "Район обязателен")
    @Size(max = 50, message = "Максимум 50 символов")
    private String district;
}
