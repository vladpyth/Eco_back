package com.example.eco_service.repositories;

import com.example.eco_service.entities.AroundBuild;
import com.example.eco_service.entities.AroundBuildCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfAroundBuildCount extends JpaRepository<AroundBuildCount, Long>, RevisionRepository<AroundBuildCount, Long, Integer> {

}
