package com.example.apoorpoor_backend.scheduler;

import com.example.apoorpoor_backend.repository.chat.ChatRepository;
import com.example.apoorpoor_backend.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatScheduler {

    private final RedisService redisService;
    private final ChatRepository chatRepository;

    @Scheduled(cron = "0 0 0 * * *") //자정
    public void saveChat() {
        Long chatRoomId = 1L;
        redisService.saveChat(chatRoomId);
    }
}
