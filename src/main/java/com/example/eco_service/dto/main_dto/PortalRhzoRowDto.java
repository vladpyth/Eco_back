package com.example.eco_service.dto.main_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Плоская строка публичного реестра РХЗО (для пагинации portal). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortalRhzoRowDto {
    private String region;
    private String groupPlaceName;
    private String registrationNumber;
    private String objectName;
    private String objectLocation;
    private String objectPhone;
    private String applicantName;
    private String applicantAddress;
    private String applicantPhone;
    private String unp;
}
