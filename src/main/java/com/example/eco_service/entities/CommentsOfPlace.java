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
public class CommentsOfPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_comments_of_place;

    @Column(nullable = false)
    private String comments;
}