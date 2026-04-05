package com.example.eco_service.dto.main_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WasteReportDto {

    // Из таблицы Region
    private String regionName;

    // Из таблицы NameGroup
    private String nameGroup;

    // Из таблицы ObjectPlaceTrash
    private String objectName;
    private String objectLocation;
    private String ownerName;
    private String locatedCompany;

    // Дополнительные поля для удобства
    private Long objectId;
    private String registrationNumber;
    private Boolean status;
}
