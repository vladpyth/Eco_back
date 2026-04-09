package com.example.eco_service.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Audited
@Table(name = "ObjectPlaceTrash")
public class ObjectPlaceTrash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_object_place_trash;

    @Column(nullable = false, unique = true, length = 10)
    private String id_registration;

    @Column(nullable = false, unique = true)
    private int register;

    @Column()
    private LocalDate date_register;

    @ManyToOne
    @JoinColumn(name = "id_сities",nullable = true)
    private Cities id_cities;

    @ManyToOne
    @JoinColumn(name = "id_group_place_save",nullable = true)
    private GroupPlaceSave id_group_place_save ;

    @ManyToOne
    @JoinColumn(name = "id_storage_scheme",nullable = true)
    private StorageScheme id_storage_scheme ;

    @ManyToOne
    @JoinColumn(name = "id_gruops_degree",nullable = true)
    private GruopsDegree id_gruops_degree ;

    @OneToMany(mappedBy = "objectPlaceTrash", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NumberPhone> phones;

    @ManyToOne
    @JoinColumn(name = "id_comments_of_place", nullable = true)
    private CommentsOfPlace id_comments_of_place ;

    @Column(nullable = false)
    private String name_obj;

    @Column(nullable = false)
    private String name_own;

    @Column()
    private int start_use;

    @Column()
    private String servise_life;

    @Column()
    private String company_located;

    @Column()
    private String place_obj;

    @Column()
    private String project;

    @Column()
    private Boolean state_expertize;

    @Column()
    private String eco_pasport;//не используется для новых мусорных станций

    @Column()
    private String prava_place;

    @Column()
    private Boolean confirmation_use;

    @Column()
    private float square;

    @Column()
    private float use_square;

    @Column()
    private float trash_square;

    @Column()
    private String project_power; //полная хуйня пересмотреть просили разделить на две части

    @Column()
    private String facticheskay_power; //полная хуйня пересмотреть просили разделить на две части

    @Column()
    private String accomulated_trash;//полная хуйня пересмотреть

    @Column()
    private String type_grounds; //не используется для новых мусорных станций

    @Column()
    private String ander_water;//не используется для новых мусорных станций

    @Column()
    private String observation_hole;

    @Column()
    private LocalDate date_axclute;

    @Column()
    private String reson_axclute;

    @Column()
    private Boolean status;
}