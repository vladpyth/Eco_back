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
@Table(name = "GroupPlaceSave")
public class GroupPlaceSave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_group_place_save;

    @Column(nullable = false, unique = true)
    private String name_region;
}
