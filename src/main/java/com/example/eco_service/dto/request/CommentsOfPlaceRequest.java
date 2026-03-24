package com.example.eco_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для сущности CommentsOfPlace")
public class CommentsOfPlaceRequest {

    @Schema(description = "Комментарий", example = "Объект в норме")
    @NotBlank(message = "comments обязателен")
    private String comments;
}
