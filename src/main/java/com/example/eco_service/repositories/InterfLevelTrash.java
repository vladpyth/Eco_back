package com.example.eco_service.repositories;


import com.example.eco_service.entities.GruopsDegree;
import com.example.eco_service.entities.LevelTrash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfLevelTrash extends JpaRepository<LevelTrash, Long>, JpaSpecificationExecutor<LevelTrash>, RevisionRepository<LevelTrash, Long, Integer> {

}
