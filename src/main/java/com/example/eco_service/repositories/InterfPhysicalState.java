package com.example.eco_service.repositories;


import com.example.eco_service.entities.ObjectPlaceTrash;
import com.example.eco_service.entities.PhysicalState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfPhysicalState extends JpaRepository<PhysicalState, Long>, JpaSpecificationExecutor<PhysicalState>, RevisionRepository<PhysicalState, Long, Integer> {

}
