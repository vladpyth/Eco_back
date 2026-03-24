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
@Table(name = "NatualSaveBuildCount")
public class NatualSaveBuildCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_natual_build_count;

    @ManyToOne
    @JoinColumn(name = "id_natual_save_build")
    private NatualSaveBuilding id_natual_save_build;

    @ManyToOne
    @JoinColumn(name = "id_object_place_trash")
    private ObjectPlaceTrash id_object_place_trash;

}
