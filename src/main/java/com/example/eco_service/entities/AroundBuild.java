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
@Table(name = "AroundBuild")
public class AroundBuild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_around_build;

    @Column(length = 255)
    private String name;

}