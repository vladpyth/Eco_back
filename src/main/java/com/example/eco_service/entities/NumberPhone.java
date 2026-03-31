package com.example.eco_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "NumberPhone")
public class NumberPhone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_phone_number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_object_place_trash")
    @JsonIgnore  // Игнорируем обратную ссылку при сериализации
    private ObjectPlaceTrash objectPlaceTrash;

    @Column(nullable = false, unique = true, length = 17)
    private String number;



}