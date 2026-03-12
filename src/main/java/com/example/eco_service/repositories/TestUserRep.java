package com.example.eco_service.repositories;

import com.example.eco_service.entities.TestUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TestUserRep extends JpaRepository<TestUser, Long> {

    Optional<TestUser> findByUsername(String username);

    Optional<TestUser> findByEmail(String email);

    List<TestUser> findByAgeGreaterThanEqual(Integer age);

    @Query("SELECT u FROM TestUser u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<TestUser> searchByFullName(@Param("name") String name);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
