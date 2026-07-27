package com.example.eco_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Audited
@Table(name = "CharacteristicTrash")
public class CharacteristicTrash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_characteristic_trash;

    @ManyToOne
    @JoinColumn(name = "id_object_place_trash")
    private ObjectPlaceTrash id_object_place_trash;


    @ManyToOne
    @JoinColumn(name = "id_magazin_trash")
    private MagazinTrash id_magazin_trash;

    @ManyToOne
    @JoinColumn(name = "id_state")
    private PhysicalState id_state;

    @Column()
    private Float weight_for_year;

    @Column()
    private Float square_for_year;

}