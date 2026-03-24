package com.example.eco_service.repositories;


import com.example.eco_service.entities.CleanerBuilds;
import com.example.eco_service.entities.CommentsOfPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfCommentsOfPlace extends JpaRepository<CommentsOfPlace, Long>, RevisionRepository<CommentsOfPlace, Long, Integer> {

}
