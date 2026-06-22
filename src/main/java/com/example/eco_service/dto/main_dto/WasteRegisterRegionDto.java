package com.example.eco_service.dto.main_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WasteRegisterRegionDto {

    private String regionName;
    private Long objectCount;
    private Double totalWeight;
    private Double totalSquare;
    private List<WasteRegisterObjectDto> objects;
}
