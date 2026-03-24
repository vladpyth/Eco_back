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
@Table(name = "LevelTrash")
public class LevelTrash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_level_trash;

    @Column(nullable = false,unique = true,  length = 150)
    private String name_level_trash;

}