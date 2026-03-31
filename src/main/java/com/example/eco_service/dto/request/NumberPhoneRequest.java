package com.example.eco_service.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для сущности NumberPhone")
public class NumberPhoneRequest {

    @Schema(description = "ID ObjectPlaceTrash", example = "1")
    @NotNull(message = "idObjectPlaceTrash обязателен")
    private Long idObjectPlaceTrash;

    @Schema(description = "Номер телефона", example = "+375291234567")
    @NotNull(message = "number обязателен")
    private String number;
}
