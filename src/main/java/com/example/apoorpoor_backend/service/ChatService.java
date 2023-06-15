package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.chat.ChatDto;
import com.example.apoorpoor_backend.model.enumType.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class ChatService{

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
}