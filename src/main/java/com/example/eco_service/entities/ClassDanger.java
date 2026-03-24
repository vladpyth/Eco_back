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
@Table(name = "ClassDanger")
public class ClassDanger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_class_danger;


    @Column(nullable = false, unique = true)
    private int class_danger;


}