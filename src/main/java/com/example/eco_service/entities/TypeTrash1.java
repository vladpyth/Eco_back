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
@Table(name = "TypeTrash1")
public class TypeTrash1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_type_trash1;

    @Column(nullable = false,unique = true,  length = 255)
    private String name_type_trash1;

}