package com.example.eco_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на создание/обновление объекта размещения отходов")
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

    @Schema(description = "ID города", example = "1")
    private Long citiesId;

    @Schema(description = "ID группы места сохранения", example = "1")
    private Long groupPlaceSaveId;

    @Schema(description = "ID схемы хранения", example = "1")
    private Long storageSchemeId;

    @Schema(description = "ID группы степени", example = "1")
    private Long gruopsDegreeId;

    @Schema(description = "ID комментариев", example = "1")
    private Long commentsOfPlaceId;

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

    @Schema(description = "Площадь (м²)", example = "10000.5")
    @PositiveOrZero(message = "Площадь должна быть положительной или 0")
    private Float square;

    @Schema(description = "Используемая площадь (м²)", example = "8000.0")
    @PositiveOrZero(message = "Используемая площадь должна быть положительной или 0")
    private Float useSquare;

    @Schema(description = "Площадь под отходы (м²)", example = "5000.0")
    @PositiveOrZero(message = "Площадь мусора должна быть положительной или 0")
    private Float trashSquare;

    @Schema(description = "Проектная мощность", example = "100 т/год")
    private String projectPower;

    @Schema(description = "Фактическая мощность", example = "95 т/год")
    private String facticheskayPower;

    @Schema(description = "Накопленные отходы", example = "описание")
    private String accomulatedTrash;

    @Schema(description = "Тип грунтов", example = "суглинок")
    private String typeGrounds;

    @Schema(description = "Грунтовые воды", example = "ниже 5 м")
    private String anderWater;

    @Schema(description = "Наблюдательная скважина", example = "СК-1")
    private String observationHole;

    @Schema(description = "Дата исключения из реестра", example = "2025-12-31")
    private LocalDate dateAxclute;

    @Schema(description = "Причина исключения", example = "Ликвидация")
    private String resonAxclute;

    @Schema(description = "Статус (активен/неактивен)", example = "true")
    private Boolean status;

    @Schema(description = "Список зданий природоохранного назначения")
    private List<NaturalSaveBuildingRequest> naturalSaveBuildings;

    @Schema(description = "Список зданий вокруг объекта")
    private List<AroundBuildRequest> aroundBuilds;

    @Schema(description = "Список телефонов")
    private List<NumberPhoneRequest> numberPhones;

    @Schema(description = "Список очистных сооружений")
    private List<CleanerBuildsRequest> cleanerBuilds;

    @Schema(description = "Список характеристик отходов")
    private List<CharacteristicTrashRequest> characteristicTrashList;

    // ==================== Внутренние классы ====================

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Здание природоохранного назначения")
    public static class NaturalSaveBuildingRequest {
        @Schema(description = "ID существующего здания", example = "10")
        private Long id;

        @Schema(description = "Название здания (для нового)", example = "Ангар №3")
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Окружающее здание")
    public static class AroundBuildRequest {
        @Schema(description = "ID существующего здания", example = "5")
        private Long id;

        @Schema(description = "Название здания (для нового)", example = "Жилой дом")
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Телефонный номер")
    public static class NumberPhoneRequest {
        @Schema(description = "ID существующего номера (при обновлении)", example = "3")
        private Long id;

        @Schema(description = "Номер телефона", example = "+375291234567")
        @NotBlank
        @Pattern(regexp = "^\\+?[0-9\\-\\s]{10,17}$", message = "Неверный формат номера")
        private String number;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Очистное сооружение")
    public static class CleanerBuildsRequest {
        @Schema(description = "ID существующего сооружения (при обновлении)", example = "2")
        private Long id;

        @Schema(description = "Регистрационный номер", example = "REG-CL-001")
        private String registrNumber;

        @Schema(description = "Название объекта", example = "Очистные №1")
        private String nameObject;

        @Schema(description = "Год ввода", example = "2010")
        private Integer startUse;

        @Schema(description = "Общая площадь (м²)", example = "500.5")
        private Float allSquare;

        @Schema(description = "Количество отходов (т)", example = "1000.0")
        private Float trashCount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Характеристика отхода")
    public static class CharacteristicTrashRequest {
        @Schema(description = "ID существующей характеристики (при обновлении)", example = "7")
        private Long id;

        @Schema(description = "ID справочника отхода", example = "12")
        private Long magazinTrashId;

        @Schema(description = "ID физического состояния", example = "1")
        private Long physicalStateId;

        @Schema(description = "Вес в год (т)", example = "250.5")
        private Float weightForYear;

        @Schema(description = "Объём в год (м³)", example = "300.0")
        private Float squareForYear;
    }
}

