package com.example.apoorpoor_backend.repository;

import com.example.apoorpoor_backend.dto.ChattingMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ChatMessageHistoryRepository extends JpaRepository<ChattingMessage, Long> {
}
