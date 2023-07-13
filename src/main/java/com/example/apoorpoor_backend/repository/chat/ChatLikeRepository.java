package com.example.apoorpoor_backend.repository.chat;

import com.example.apoorpoor_backend.model.ChatLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatLikeRepository extends JpaRepository<ChatLike, Long> {
    Optional<ChatLike> findByBeggarIdAndChatId(Long beggar_id, Long chatId);
    void deleteByBeggarIdAndChatId(Long beggar_id, Long chatId);
    long countByChatId(Long chatId);
}