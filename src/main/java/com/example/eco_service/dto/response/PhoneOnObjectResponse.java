package com.example.eco_service.dto.response;

import com.example.eco_service.entities.NumberPhoneCount;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Номер телефона в контексте объекта (из промежуточной таблицы). */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneOnObjectResponse {

    @JsonProperty("id_phone_number")
    private Long id_phone_number;
    private String number;
    /** 0 — юр. лицо, 1 — объект, 3 — оба варианта для данной связи */
    private int ur_ob;

    public static PhoneOnObjectResponse from(NumberPhoneCount link) {
        var p = link.getId_phone_number();
        return new PhoneOnObjectResponse(
                p.getId_phone_number(),
                p.getNumber(),
                link.getUr_ob()
        );
    }
}
