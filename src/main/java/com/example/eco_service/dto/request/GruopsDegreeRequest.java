package com.example.eco_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для сущности GruopsDegree")
public class GruopsDegreeRequest {

    @Schema(description = "Номер группы", example = "1")
    @NotNull(message = "namberGruop обязателен")
    private Integer namberGruop;
}
