package com.example.eco_service.repositories;

import com.example.eco_service.entities.NumberPhoneCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterfNumberPhoneCount extends JpaRepository<NumberPhoneCount, Long>, RevisionRepository<NumberPhoneCount, Long, Integer> {
    @Query("select c from NumberPhoneCount c where c.id_object_place_trash.id_object_place_trash = :objectPlaceId")
    List<NumberPhoneCount> findAllByObjectPlaceId(@Param("objectPlaceId") Long objectPlaceId);

    @Query("""
            select c from NumberPhoneCount c
            where c.id_object_place_trash.id_object_place_trash = :objectPlaceId
              and c.id_phone_number.id_phone_number = :phoneId
            """)
    Optional<NumberPhoneCount> findLink(
            @Param("objectPlaceId") Long objectPlaceId,
            @Param("phoneId") Long phoneId);

    @Query("""
            select count(c) > 0
            from NumberPhoneCount c
            where c.id_object_place_trash.id_object_place_trash = :objectPlaceId
              and c.id_phone_number.id_phone_number = :phoneId
            """)
    boolean existsLink(@Param("objectPlaceId") Long objectPlaceId, @Param("phoneId") Long phoneId);

    @Modifying
    @Query("""
            delete from NumberPhoneCount c
            where c.id_object_place_trash.id_object_place_trash = :objectPlaceId
              and c.id_phone_number.id_phone_number = :phoneId
            """)
    void deleteLink(@Param("objectPlaceId") Long objectPlaceId, @Param("phoneId") Long phoneId);

    @Modifying
    @Query("delete from NumberPhoneCount c where c.id_phone_number.id_phone_number = :phoneId")
    void deleteAllByPhoneId(@Param("phoneId") Long phoneId);
}

