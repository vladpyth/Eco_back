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
@Table(name = "NatualSaveBuilding")
public class NatualSaveBuilding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_natual_save_build;

    @Column(length = 150)
    private String name;

}