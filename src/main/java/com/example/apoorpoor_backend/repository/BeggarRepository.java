package com.example.apoorpoor_backend.repository;

import com.example.apoorpoor_backend.model.Beggar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeggarRepository extends JpaRepository<Beggar, Long> {
    Optional<Beggar> findByUserId(Long id);
}