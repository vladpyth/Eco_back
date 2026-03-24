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
@Schema(description = "Запрос для сущности CleanerBuilds")
public class CleanerBuildsRequest {

    @Schema(description = "Регистрационный номер", example = "REG-2024-001")
    @NotBlank(message = "registrNumber обязателен")
    @Size(max = 15, message = "Максимум 15 символов")
    private String registrNumber;

    @Schema(description = "ID ObjectPlaceTrash", example = "1")
    @NotNull(message = "idObjectPlaceTrash обязателен")
    private Long idObjectPlaceTrash;

    @Schema(description = "Название объекта", example = "Очистное сооружение")
    @NotBlank(message = "nameObject обязателен")
    @Size(max = 50, message = "Максимум 50 символов")
    private String nameObject;

    @Schema(description = "Год начала эксплуатации", example = "2010")
    private Integer startUse;

    @Schema(description = "Общая площадь", example = "500.5")
    private Float allSquare;

    @Schema(description = "Объём отходов", example = "120.0")
    private Float trashCount;
}
