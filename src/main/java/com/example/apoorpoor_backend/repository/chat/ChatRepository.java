package com.example.apoorpoor_backend.repository.chat;

import com.example.apoorpoor_backend.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ChatRepository extends JpaRepository<Chat, Long> {
}
