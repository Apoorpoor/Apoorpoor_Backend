package com.example.apoorpoor_backend.repository;


import com.example.apoorpoor_backend.entity.Beggar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface BeggarRepository extends JpaRepository<Beggar, Long> {
    Beggar findByUserId(Long id);
}
