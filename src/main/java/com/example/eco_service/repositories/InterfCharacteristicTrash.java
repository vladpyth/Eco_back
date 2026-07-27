package com.example.eco_service.repositories;

import com.example.eco_service.entities.CharacteristicTrash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterfCharacteristicTrash extends JpaRepository<CharacteristicTrash, Long>, JpaSpecificationExecutor<CharacteristicTrash>, RevisionRepository<CharacteristicTrash, Long, Integer> {

    @Query("SELECT c FROM CharacteristicTrash c WHERE c.id_object_place_trash.id_object_place_trash = :objectId")
    List<CharacteristicTrash> findAllByObjectPlaceTrashId(@Param("objectId") Long objectId);
}
