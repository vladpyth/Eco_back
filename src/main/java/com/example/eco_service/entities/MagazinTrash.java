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
@Table(name = "MagazinTrash")
public class MagazinTrash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_magazin_trash;

    @OneToOne
    @JoinColumn(name = "id_class_danger")
    private ClassDanger id_class_danger;

    @OneToOne
    @JoinColumn(name = "id_type_trash")
    private TypeTrash1 id_type_trash;

    @OneToOne
    @JoinColumn(name = "id_level_trash")
    private LevelTrash id_level_trash;

    @OneToOne
    @JoinColumn(name = "id_mame_group")
    private NameGroup id_mame_group;

    @Column(nullable = false, unique = true, length = 8)
    private String code_trash;

    @Column(nullable = false,  length = 50)
    private String name_trash;

    @Column(nullable = false)
    private int level1;

    @Column(nullable = false)
    private int group2;

    @Column(nullable = false)
    private int level3;

    @Column(nullable = false)
    private String level4;
}