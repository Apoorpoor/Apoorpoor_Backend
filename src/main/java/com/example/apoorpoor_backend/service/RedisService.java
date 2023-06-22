package com.example.apoorpoor_backend.service;


import com.example.apoorpoor_backend.model.Chat;
import com.example.apoorpoor_backend.repository.chat.ChatRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;


@Service
@RequiredArgsConstructor
public class RedisService {

    //    private final RedisTemplate<String, String> redisTemplate;
    private final ChatRepository chatRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /*
    Redis에 채팅 내역 가져오기
    */
    public List<Chat> getChats(Long chatRoomId){

        String chatListIdKey = "ChatListId" + chatRoomId;
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        List<String> chatList = listOperations.range(chatListIdKey, 0, -1);

        List<String> chatValues = new ArrayList<>();

        for (String chatKey : chatList) {
            String chatValue = redisTemplate.opsForValue().get(chatKey);
            chatValues.add(chatValue);
        }

        List<Chat> chatTotalList = new ArrayList<>();

        try {
            for (String s : chatValues) {
                Chat chat = objectMapper.readValue(s, Chat.class);
                chatTotalList.add(chat);
            }
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
        return chatTotalList;

    }

    /*
    Redis에 채팅 저장
    */
    public void setChatValues(Chat chat, Long chatRoomId, String chatId) {
        try{
            String chatKey = "Chats" + chatRoomId+":"+chatId;
            String chatJson = objectMapper.writeValueAsString(chat);
            redisTemplate.opsForValue().set(chatKey, chatJson);
            redisTemplate.expire(chatKey,Duration.ofDays(1));

            String chatListIdKey = "ChatListId" + chatRoomId;
            redisTemplate.opsForList().rightPush(chatListIdKey, chatKey);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    /*
    Redis에 저장된 채팅 정보를 데이터베이스에 저장하는 역할
     */
    public void saveChat(Long chatRoomId){
        List<Chat> chats = getChats(chatRoomId);
        chatRepository.saveAll(chats);
        redisTemplate.delete("Chat"+chatRoomId);
    }

}