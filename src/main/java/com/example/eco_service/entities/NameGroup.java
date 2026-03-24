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
@Table(name = "NameGroup")
public class NameGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_mame_group;

    @Column(nullable = false,unique = true,  length = 150)
    private String name_group;

}