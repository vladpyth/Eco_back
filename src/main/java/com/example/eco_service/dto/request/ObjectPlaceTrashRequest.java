package com.example.eco_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для сущности ObjectPlaceTrash")
public class ObjectPlaceTrashRequest {

    @Schema(description = "Регистрационный идентификатор", example = "REG-000001")
    @NotBlank(message = "idRegistration обязателен")
    @Size(max = 10, message = "Максимум 10 символов")
    private String idRegistration;

    @Schema(description = "Регистрационный номер", example = "10001")
    @NotNull(message = "register обязателен")
    private Integer register;

    @Schema(description = "Дата регистрации", example = "2024-01-15")
    private LocalDate dateRegister;

    @Schema(description = "ID Cities", example = "1")
    private Long idCities;

    @Schema(description = "ID GroupPlaceSave", example = "1")
    private Long idGroupPlaceSave;

    @Schema(description = "ID StorageScheme", example = "1")
    private Long idStorageScheme;

    @Schema(description = "ID GruopsDegree", example = "1")
    private Long idGruopsDegree;

    @Schema(description = "ID CommentsOfPlace", example = "1")
    private Long idCommentsOfPlace;

    @Schema(description = "Название объекта", example = "Полигон ТБО")
    @NotBlank(message = "nameObj обязателен")
    private String nameObj;

    @Schema(description = "Наименование владельца", example = "ООО Эко")
    @NotBlank(message = "nameOwn обязателен")
    private String nameOwn;

    @Schema(description = "Год ввода в эксплуатацию", example = "2015")
    private Integer startUse;

    @Schema(description = "Срок службы", example = "25 лет")
    private String serviseLife;

    @Schema(description = "Организация на территории", example = "ООО Утилизация")
    private String companyLocated;

    @Schema(description = "Местоположение объекта", example = "ул. Лесная, 1")
    private String placeObj;

    @Schema(description = "Проект", example = "П-2020")
    private String project;

    @Schema(description = "Состояние экспертизы", example = "true")
    private Boolean stateExpertize;

    @Schema(description = "Экологический паспорт", example = "№123")
    private String ecoPasport;

    @Schema(description = "Права на участок", example = "Аренда")
    private String pravaPlace;

    @Schema(description = "Подтверждение использования", example = "true")
    private Boolean confirmationUse;

    @Schema(description = "Площадь", example = "10000.5")
    private Float square;

    @Schema(description = "Используемая площадь", example = "8000.0")
    private Float useSquare;

    @Schema(description = "Площадь под отходы", example = "5000.0")
    private Float trashSquare;

    @Schema(description = "Мощность", example = "100 т/сут")
    private String power;

    @Schema(description = "Накопленные отходы", example = "описание")
    private String accomulatedTrash;

    @Schema(description = "Тип грунтов", example = "суглинок")
    private String typeGrounds;

    @Schema(description = "Грунтовые воды", example = "ниже 5 м")
    private String anderWater;

    @Schema(description = "Наблюдательная скважина", example = "СК-1")
    private String observationHole;

    @Schema(description = "Дата исключения", example = "2025-12-31")
    private LocalDate dateAxclute;

    @Schema(description = "Причина исключения", example = "Ликвидация")
    private String resonAxclute;

    @Schema(description = "Статус", example = "true")
    private Boolean status;
}
