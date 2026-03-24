package com.example.eco_service.repositories;


import com.example.eco_service.entities.Region;
import com.example.eco_service.entities.StorageScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfStorageScheme extends JpaRepository<StorageScheme, Long>, RevisionRepository<StorageScheme, Long, Integer> {

}
