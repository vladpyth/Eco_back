package com.example.eco_service.repositories;


import com.example.eco_service.entities.StorageScheme;
import com.example.eco_service.entities.TypeTrash1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfTypeTrash1 extends JpaRepository<TypeTrash1, Long>, RevisionRepository<TypeTrash1, Long, Integer> {

}
