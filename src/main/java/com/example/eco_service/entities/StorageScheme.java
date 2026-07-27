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
@Table(name = "StorageScheme")
public class StorageScheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_storage_scheme;

    @Column(nullable = false, unique = true, length = 255)
    private String name_storage_scheme;
}
