package com.example.eco_service.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ эхо-сервиса")
public class EchoResponse {

    @Schema(description = "Флаг получения данных", example = "true")
    private boolean received;

    @Schema(description = "Время получения запроса")
    private LocalDateTime timestamp;

    @Schema(description = "Имя пользователя", example = "Анна")
    private String name;

    @Schema(description = "Возраст пользователя", example = "25")
    private Integer age;
}


