package com.example.apoorpoor_backend.repository;

import com.example.apoorpoor_backend.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRepository extends JpaRepository<Chat, Long> {
}
