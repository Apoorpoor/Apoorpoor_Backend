package com.example.apoorpoor_backend.repository.beggar;

import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeggarRepository extends JpaRepository<Beggar, Long>, BeggarRepositoryCustom{
    Optional<Beggar> findByUserId(Long id);

//    @Query("SELECT b FROM BEGGAR b JOIN USERS u ON b.user.id = u.id WHERE b.user.username = :username")
//    Optional<Beggar> findByUsername(@Param("username") String username);
}