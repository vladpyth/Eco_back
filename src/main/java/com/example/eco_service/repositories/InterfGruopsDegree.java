package com.example.eco_service.repositories;


import com.example.eco_service.entities.GroupPlaceSave;
import com.example.eco_service.entities.GruopsDegree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfGruopsDegree extends JpaRepository<GruopsDegree, Long>, RevisionRepository<GruopsDegree, Long, Integer> {

}
