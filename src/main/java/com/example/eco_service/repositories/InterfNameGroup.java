package com.example.eco_service.repositories;


import com.example.eco_service.entities.MagazinTrash;
import com.example.eco_service.entities.NameGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfNameGroup extends JpaRepository<NameGroup, Long>, JpaSpecificationExecutor<NameGroup>, RevisionRepository<NameGroup, Long, Integer> {

}
