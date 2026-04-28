package com.example.eco_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.Audited;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Audited
@Table(name = "NumberPhoneCount")
public class NumberPhoneCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_number_phone_count;

    @ManyToOne
    @JoinColumn(name = "id_phone_number")
    private NumberPhone id_phone_number;

    @ManyToOne
    @JoinColumn(name = "id_object_place_trash")
    @JsonIgnore
    private ObjectPlaceTrash id_object_place_trash;

    /** Принадлежность номера для данной связи объект–номер: 0 юр. лицо, 1 объект, 3 оба */
    @Column(nullable = false)
    @ColumnDefault("0")
    private int ur_ob;
}

