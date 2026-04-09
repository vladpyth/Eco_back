package com.example.eco_service.repositories;

import com.example.eco_service.entities.AroundBuild;
import com.example.eco_service.entities.AroundBuildCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterfAroundBuildCount extends JpaRepository<AroundBuildCount, Long>, RevisionRepository<AroundBuildCount, Long, Integer> {
    @Query("select c from AroundBuildCount c where c.id_object_place_trash.id_object_place_trash = :objectPlaceId")
    List<AroundBuildCount> findAllByObjectPlaceId(@Param("objectPlaceId") Long objectPlaceId);

    @Query("""
            select count(c) > 0
            from AroundBuildCount c
            where c.id_object_place_trash.id_object_place_trash = :objectPlaceId
              and c.id_around_build.id_around_build = :aroundBuildId
            """)
    boolean existsLink(@Param("objectPlaceId") Long objectPlaceId, @Param("aroundBuildId") Long aroundBuildId);

    @Modifying
    @Query("""
            delete from AroundBuildCount c
            where c.id_object_place_trash.id_object_place_trash = :objectPlaceId
              and c.id_around_build.id_around_build = :aroundBuildId
            """)
    void deleteLink(@Param("objectPlaceId") Long objectPlaceId, @Param("aroundBuildId") Long aroundBuildId);
}
