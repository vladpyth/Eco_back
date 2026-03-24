package com.example.eco_service.repositories;


import com.example.eco_service.entities.NameGroup;
import com.example.eco_service.entities.NatualSaveBuildCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfNatualSaveBuildCount extends JpaRepository<NatualSaveBuildCount, Long>, RevisionRepository<NatualSaveBuildCount, Long, Integer> {

}
