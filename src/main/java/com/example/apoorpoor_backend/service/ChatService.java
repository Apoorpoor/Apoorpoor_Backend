package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.chat.ChatDto;
import com.example.apoorpoor_backend.dto.chat.ChatListDto;
import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.Chat;
import com.example.apoorpoor_backend.model.enumType.MessageType;
import com.example.apoorpoor_backend.repository.beggar.BeggarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ChatService{

    private final BeggarRepository beggarRepository;
    private final List<ChatListDto> chatParticipantsList = new ArrayList<>();

    public ChatDto enterChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor, ChatListDto chatListDto) {
        headerAccessor.getSessionAttributes().put("beggar_id", chatDto.getBeggar_id());
        headerAccessor.getSessionAttributes().put("nickName", chatDto.getSender());
        headerAccessor.getSessionAttributes().put("userId", chatDto.getUserId());
        chatDto.setMessage(chatDto.getSender() + "님이 입장하셨습니다.");

        Long beggarId = chatDto.getBeggar_id();
        chatListDto.getChatList().add(beggarId);

        chatParticipantsList.add(chatListDto);

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


        chatParticipantsList.stream()
                .filter(chatListDto -> chatListDto.getChatList().contains(beggar_id))
                .findFirst()
                .ifPresent(chatListDto -> {
                    chatListDto.getChatList().remove(beggar_id);
                });

        return chatDto;
    }

    public void sendChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {
        Beggar beggar = beggarCheck(chatDto.getBeggar_id());
        MessageType type = MessageType.TALK;
        Chat chat = Chat.builder()
                .sender(chatDto.getSender())
                .message(chatDto.getMessage())
                .beggar(beggar)
                .type(type)
                .build();
    }

    public List<ChatListDto> getChatParticipants() {
        return new ArrayList<>(chatParticipantsList);
    }

    public Beggar beggarCheck(Long beggar_id) {
        return beggarRepository.findById(beggar_id).orElseThrow(
                () -> new IllegalArgumentException("푸어를 찾을 수 없습니다.")
        );
    }
}