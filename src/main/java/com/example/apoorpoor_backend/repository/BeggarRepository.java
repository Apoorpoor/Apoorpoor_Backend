package com.example.apoorpoor_backend.repository;

import com.example.apoorpoor_backend.model.Beggar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BeggarRepository extends JpaRepository<Beggar, Long>, BeggarRepositoryCustom{
    Optional<Beggar> findByUserId(Long id);

    @Query("SELECT b FROM BEGGAR b JOIN USERS u ON b.user.id = u.id WHERE b.user.username = :username")
    Optional<Beggar> findByUsername(@Param("username") String username);
}