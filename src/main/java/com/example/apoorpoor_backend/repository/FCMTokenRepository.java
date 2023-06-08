package com.example.apoorpoor_backend.repository;

import com.example.apoorpoor_backend.model.FCMToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FCMTokenRepository extends JpaRepository<FCMToken, Long> {
}
