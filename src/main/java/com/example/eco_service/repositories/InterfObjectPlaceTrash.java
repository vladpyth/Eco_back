package com.example.eco_service.repositories;


import com.example.eco_service.entities.ObjectPlaceTrash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfObjectPlaceTrash extends JpaSpecificationExecutor<ObjectPlaceTrash>, JpaRepository<ObjectPlaceTrash, Long>, RevisionRepository<ObjectPlaceTrash , Long, Integer> {

    @Query("select (count(o) > 0) from ObjectPlaceTrash o where o.id_registration = :reg")
    boolean existsByRegistrationNumber(@Param("reg") String reg);

    @Query("select (count(o) > 0) from ObjectPlaceTrash o where o.id_registration = :reg and o.id_object_place_trash <> :id")
    boolean existsByRegistrationNumberAndIdNot(@Param("reg") String reg, @Param("id") Long id);
}
