package com.example.eco_service.repositories;

import com.example.eco_service.entities.NatualSaveBuilding;
import com.example.eco_service.entities.NumberPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InterfNumberPhone extends JpaRepository<NumberPhone, Long>, RevisionRepository<NumberPhone , Long, Integer> {

}
