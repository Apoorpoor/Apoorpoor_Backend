package com.example.apoorpoor_backend.repository.beggar;

import com.example.apoorpoor_backend.dto.common.StatusResponseDto;
import com.example.apoorpoor_backend.model.Beggar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface BeggarRepository extends JpaRepository<Beggar, Long>, BeggarRepositoryCustom{
    Optional<Beggar> findByUserId(Long id);

    Optional<Beggar> findByNickname(String nickname);
}