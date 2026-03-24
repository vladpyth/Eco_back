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
@Table(name = "Cities")
public class Cities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cities;

    @ManyToOne
    @JoinColumn(name = "id_region")
    private Region id_region;

    @Column(nullable = false, unique = true, length = 7)
    private String index;

    @Column(nullable = false,  length = 50)
    private String district;

}
