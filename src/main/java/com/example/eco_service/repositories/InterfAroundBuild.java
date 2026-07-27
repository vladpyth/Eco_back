package com.example.eco_service.repositories;

import com.example.eco_service.entities.AroundBuild;
import com.example.eco_service.entities.TestUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterfAroundBuild extends JpaRepository<AroundBuild, Long>, JpaSpecificationExecutor<AroundBuild>, RevisionRepository<AroundBuild, Long, Integer> {

}
