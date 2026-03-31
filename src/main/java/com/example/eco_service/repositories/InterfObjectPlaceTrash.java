package com.example.eco_service.repositories;


import com.example.eco_service.entities.NatualSaveBuilding;
import com.example.eco_service.entities.ObjectPlaceTrash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfObjectPlaceTrash extends JpaSpecificationExecutor<ObjectPlaceTrash>, JpaRepository<ObjectPlaceTrash, Long>, RevisionRepository<ObjectPlaceTrash , Long, Integer> {

}
