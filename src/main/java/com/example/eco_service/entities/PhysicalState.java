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
@Table(name = "PhysicalState")
public class PhysicalState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_state;


    @Column(nullable = false, unique = true, length = 22)
    private String state;


}