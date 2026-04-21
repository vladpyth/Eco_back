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
@Schema(description = "Запрос для сущности MagazinTrash")
public class MagazinTrashRequest {

    @Schema(description = "ID ClassDanger; отсутствует/null/<=0/-1 — без класса опасности", example = "1")
    private Long idClassDanger;

    @Schema(description = "ID TypeTrash1", example = "1")
    @NotNull(message = "idTypeTrash обязателен")
    private Long idTypeTrash;

    @Schema(description = "ID LevelTrash", example = "1")
    @NotNull(message = "idLevelTrash обязателен")
    private Long idLevelTrash;

    @Schema(description = "ID NameGroup", example = "1")
    @NotNull(message = "idMameGroup обязателен")
    private Long idMameGroup;

    @Schema(description = "Код отходов", example = "12345678")
    @NotBlank(message = "codeTrash обязателен")
    @Size(max = 8, message = "Максимум 8 символов")
    private String codeTrash;

    @Schema(description = "Название отходов", example = "ТБО смешанные")
    @NotBlank(message = "nameTrash обязателен")
    @Size(max = 50, message = "Максимум 50 символов")
    private String nameTrash;

    @Schema(description = "\"block 1", example = "1")
    @NotNull(message = "block1 обязателен")
    private Integer block1;

    @Schema(description = "Группа 2", example = "2")
    @NotNull(message = "group2 обязателен")
    private Integer group2;

    @Schema(description = "group3", example = "3")
    @NotNull(message = "group3 обязателен")
    private Integer group3;


}
