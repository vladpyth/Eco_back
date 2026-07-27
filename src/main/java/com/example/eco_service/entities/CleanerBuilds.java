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
@Table(name = "CleanerBuilds")
public class CleanerBuilds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cleaner_build;

    @Column(nullable = false, length = 15)
    private String registr_number;

    @ManyToOne
    @JoinColumn(name = "id_object_place_trash")
    private ObjectPlaceTrash id_object_place_trash;


    @Column(nullable = false, length = 255)
    private String name_object;

    @Column()
    private Integer start_use;

    @Column()
    private Float all_square;

    @Column()
    private Float trash_count;

}
