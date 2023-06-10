package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.chat.ChatDto;
import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.Chat;
import com.example.apoorpoor_backend.model.enumType.MessageType;
import com.example.apoorpoor_backend.repository.beggar.BeggarRepository;
import com.example.apoorpoor_backend.repository.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final BeggarRepository beggarRepository;
    //private final ChatRepository chatRepository;

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
                .message(nickName + "님이 퇴장하셨습니다.")
                .userId(userId)
                .build();
        return chatDto;
    }

    public void sendChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {
        Beggar beggar = beggarCheck(chatDto.getBeggar_id());
        MessageType type = MessageType.TALK;

// Date date = new Date();
// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
// String dateformat = format.format(date);
// chatDto.setDate(dateformat);
// Chat chat = new Chat(chatDto,beggar, type);
    }

    public Beggar beggarCheck(Long beggar_id) {
        return beggarRepository.findById(beggar_id).orElseThrow(
                () -> new IllegalArgumentException("푸어를 찾을 수 없습니다.")
        );
    }
}
