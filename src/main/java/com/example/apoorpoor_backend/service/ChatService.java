package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.chat.ChatDto;
import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.Chat;
import com.example.apoorpoor_backend.model.enumType.MessageType;
import com.example.apoorpoor_backend.repository.beggar.BeggarRepository;
import com.example.apoorpoor_backend.repository.chat.ChatRepository;
//import com.google.common.cache.CacheBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;

import org.springframework.cache.annotation.Cacheable;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.data.redis.core.ValueOperations;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final BeggarRepository beggarRepository;
    //    private final ChatRepository chatRepository;
//    private final RedisTemplate<String, ChatDto> chatRedisTemplate;
//    private static final String CHAT_LIKES_CACHE_KEY = "chat_likes";
//
    public ChatDto enterChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("beggar_id", chatDto.getBeggar_id());
        headerAccessor.getSessionAttributes().put("nickName", chatDto.getSender());
        headerAccessor.getSessionAttributes().put("userId", chatDto.getUserId());
        chatDto.setMessage(chatDto.getSender() + "님이 입장하셨습니다.");
        return chatDto;
    }
    public ChatDto disconnectChatRoom(SimpMessageHeaderAccessor headerAccessor) {
        String nickName = (String) headerAccessor.getSessionAttributes().get("nickName");
        Long beggar_id = (Long) headerAccessor.getSessionAttributes().get("beggar_id");
        Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");
        ChatDto chatDto = ChatDto.builder()
                .type(MessageType.LEAVE)
                .sender(nickName)
                .beggar_id(beggar_id)
                .level(1L)
                .message(nickName + "님이 퇴장하셨습니다.")
                .userId(userId)
                .build();
        return chatDto;
    }
    public void sendChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {
        Beggar beggar = beggarCheck(chatDto.getBeggar_id());
        MessageType type = MessageType.TALK;
//        Date date = new Date();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateformat = format.format(date);
//        chatDto.setDate(dateformat);
        Chat chat = new Chat(chatDto,beggar, type);
    }
//
//    @CachePut(value = "chatLikes", key = "#chatId")
//   public void addLikeToChatMessage(Long chatId) {
//        Chat chat = chatRepository.findById(chatId).orElseThrow(
//                () -> new IllegalArgumentException("채팅 메시지를 찾을 수 없습니다.")
//        );
//        chat.setLikes(); // 좋아요 수 증가
//        chatRepository.save(chat);
//        // Redis 캐시에 저장
//        String cacheKey = generateCacheKey(chatId);
//        ValueOperations<String, ChatDto> valueOperations = chatRedisTemplate.opsForValue();
//        valueOperations.set(cacheKey, new ChatDto(chat), 30, TimeUnit.DAYS); // TTL 30일 설정
//    }
//        private String generateCacheKey(Long chatId) {
//            return CHAT_LIKES_CACHE_KEY + ":" + chatId;
//        }
//
    public Beggar beggarCheck(Long beggar_id) {
        return beggarRepository.findById(beggar_id).orElseThrow(
                () -> new IllegalArgumentException("푸어를 찾을 수 없습니다.")
        );
    }
}