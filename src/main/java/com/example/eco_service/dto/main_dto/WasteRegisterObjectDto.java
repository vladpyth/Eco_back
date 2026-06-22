package com.example.eco_service.dto.main_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WasteRegisterObjectDto {

    private String objectName;
    private String objectLocation;
    private String ownerName;
    private String companyLocated;
    private String phonesLegal;
    private String phonesOwner;
    private String phones;
    private String groupPlaceName;
    private String status;
    private String registrationNumber;
    private String payerIdentificationNumber;
    private Integer startUse;
    private Float square;
    private String wasteGroups;
}
