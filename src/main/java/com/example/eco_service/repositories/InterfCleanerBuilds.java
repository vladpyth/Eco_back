package com.example.eco_service.repositories;


import com.example.eco_service.entities.ClassDanger;
import com.example.eco_service.entities.CleanerBuilds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfCleanerBuilds extends JpaRepository<CleanerBuilds, Long>, RevisionRepository<CleanerBuilds, Long, Integer> {

}
