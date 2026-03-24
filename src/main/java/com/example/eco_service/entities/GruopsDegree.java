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
@Table(name = "GruopsDegree")
public class GruopsDegree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_gruops_degree;

    @Column(nullable = false, unique = true)
    private int namber_gruop;


}