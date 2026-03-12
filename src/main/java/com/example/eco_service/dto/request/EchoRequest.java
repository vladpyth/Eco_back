package com.example.eco_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для эхо-сервиса")
public class EchoRequest {

    @Schema(description = "Имя пользователя", example = "Анна")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    private String name;

    @Schema(description = "Возраст пользователя", example = "25")
    @Min(value = 0, message = "Возраст не может быть отрицательным")
    private Integer age;
}