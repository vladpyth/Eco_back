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
@Table(name = "AroundBuildCount")
public class AroundBuildCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_around_build_count;

    @ManyToOne
    @JoinColumn(name = "id_around_build")
    private AroundBuild id_around_build;

    @ManyToOne
    @JoinColumn(name = "id_object_place_trash")
    private ObjectPlaceTrash id_object_place_trash;

}
