package com.example.eco_service.dto.response;

import com.example.eco_service.entities.Cities;
import com.example.eco_service.entities.GroupPlaceSave;
import com.example.eco_service.entities.GruopsDegree;
import com.example.eco_service.entities.ObjectPlaceTrash;
import com.example.eco_service.entities.Region;
import com.example.eco_service.entities.StorageScheme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Lightweight representation used by object list endpoints.
 * Deliberately excludes phones and all object-owned collections.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObjectPlaceTrashListResponse {
    private Long id_object_place_trash;
    private String id_registration;
    private int register;
    private LocalDate date_register;
    private Cities id_cities;
    private Region id_region;
    private GroupPlaceSave id_group_place_save;
    private StorageScheme id_storage_scheme;
    private GruopsDegree id_gruops_degree;
    private String name_obj;
    private String name_own;
    private Integer start_use;
    private String servise_life;
    private String company_located;
    private String payer_indentification_number;
    private String place_obj;
    private String project;
    private Boolean state_expertize;
    private String eco_pasport;
    private String prava_place;
    private Boolean confirmation_use;
    private Float square;
    private Float use_square;
    private Float trash_square;
    private String project_power;
    private String facticheskay_power;
    private String accomulated_trash;
    private String type_grounds;
    private String ander_water;
    private String observation_hole;
    private LocalDate date_axclute;
    private String reson_axclute;
    private Boolean status;

    public static ObjectPlaceTrashListResponse from(ObjectPlaceTrash entity) {
        return ObjectPlaceTrashListResponse.builder()
                .id_object_place_trash(entity.getId_object_place_trash())
                .id_registration(entity.getId_registration())
                .register(entity.getRegister())
                .date_register(entity.getDate_register())
                .id_cities(entity.getId_cities())
                .id_region(entity.getId_region())
                .id_group_place_save(entity.getId_group_place_save())
                .id_storage_scheme(entity.getId_storage_scheme())
                .id_gruops_degree(entity.getId_gruops_degree())
                .name_obj(entity.getName_obj())
                .name_own(entity.getName_own())
                .start_use(entity.getStart_use())
                .servise_life(entity.getServise_life())
                .company_located(entity.getCompany_located())
                .payer_indentification_number(entity.getPayer_indentification_number())
                .place_obj(entity.getPlace_obj())
                .project(entity.getProject())
                .state_expertize(entity.getState_expertize())
                .eco_pasport(entity.getEco_pasport())
                .prava_place(entity.getPrava_place())
                .confirmation_use(entity.getConfirmation_use())
                .square(entity.getSquare())
                .use_square(entity.getUse_square())
                .trash_square(entity.getTrash_square())
                .project_power(entity.getProject_power())
                .facticheskay_power(entity.getFacticheskay_power())
                .accomulated_trash(entity.getAccomulated_trash())
                .type_grounds(entity.getType_grounds())
                .ander_water(entity.getAnder_water())
                .observation_hole(entity.getObservation_hole())
                .date_axclute(entity.getDate_axclute())
                .reson_axclute(entity.getReson_axclute())
                .status(entity.getStatus())
                .build();
    }
}
