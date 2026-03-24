package com.example.eco_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для сущности CharacteristicTrash")
public class CharacteristicTrashRequest {

    @Schema(description = "ID ObjectPlaceTrash", example = "1")
    @NotNull(message = "idObjectPlaceTrash обязателен")
    private Long idObjectPlaceTrash;

    @Schema(description = "ID MagazinTrash", example = "1")
    @NotNull(message = "idMagazinTrash обязателен")
    private Long idMagazinTrash;

    @Schema(description = "ID PhysicalState", example = "1")
    @NotNull(message = "idState обязателен")
    private Long idState;

    @Schema(description = "Вес за год", example = "12.5")
    private Float weightForYear;

    @Schema(description = "Площадь за год", example = "30.0")
    private Float squareForYear;
}
