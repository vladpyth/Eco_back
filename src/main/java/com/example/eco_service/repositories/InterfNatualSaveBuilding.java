package com.example.eco_service.repositories;


import com.example.eco_service.entities.NatualSaveBuildCount;
import com.example.eco_service.entities.NatualSaveBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfNatualSaveBuilding extends JpaRepository<NatualSaveBuilding, Long>, JpaSpecificationExecutor<NatualSaveBuilding>, RevisionRepository<NatualSaveBuilding , Long, Integer> {

}
