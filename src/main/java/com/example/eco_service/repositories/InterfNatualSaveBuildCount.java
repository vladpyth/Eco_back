package com.example.eco_service.repositories;


import com.example.eco_service.entities.NatualSaveBuildCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterfNatualSaveBuildCount extends JpaRepository<NatualSaveBuildCount, Long>, RevisionRepository<NatualSaveBuildCount, Long, Integer> {
    @Query("select c from NatualSaveBuildCount c where c.id_object_place_trash.id_object_place_trash = :objectPlaceId")
    List<NatualSaveBuildCount> findAllByObjectPlaceId(@Param("objectPlaceId") Long objectPlaceId);

    @Query("""
            select count(c) > 0
            from NatualSaveBuildCount c
            where c.id_object_place_trash.id_object_place_trash = :objectPlaceId
              and c.id_natual_save_build.id_natual_save_build = :natualSaveBuildId
            """)
    boolean existsLink(@Param("objectPlaceId") Long objectPlaceId, @Param("natualSaveBuildId") Long natualSaveBuildId);

    @Modifying
    @Query("""
            delete from NatualSaveBuildCount c
            where c.id_object_place_trash.id_object_place_trash = :objectPlaceId
              and c.id_natual_save_build.id_natual_save_build = :natualSaveBuildId
            """)
    void deleteLink(@Param("objectPlaceId") Long objectPlaceId, @Param("natualSaveBuildId") Long natualSaveBuildId);
}
