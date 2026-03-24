package com.example.eco_service.repositories;


import com.example.eco_service.entities.CommentsOfPlace;
import com.example.eco_service.entities.GroupPlaceSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfGroupPlaceSave extends JpaRepository<GroupPlaceSave, Long>, RevisionRepository<GroupPlaceSave, Long, Integer> {

}
