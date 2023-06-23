package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.chat.BadWordFiltering;
import com.example.apoorpoor_backend.dto.chat.ChatDto;
import com.example.apoorpoor_backend.dto.chat.ChatImagesDto;
import com.example.apoorpoor_backend.dto.chat.ChatListDto;
import com.example.apoorpoor_backend.dto.chat.ChatRoomDto;
import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.Chat;
import com.example.apoorpoor_backend.model.Image;
import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.model.enumType.MessageType;
import com.example.apoorpoor_backend.repository.ImageRepository;
import com.example.apoorpoor_backend.repository.beggar.BeggarRepository;
import com.example.apoorpoor_backend.repository.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final BeggarRepository beggarRepository;
    private final ChatRepository chatRepository;
    private final ImageRepository imageRepository;
    private final SimpMessagingTemplate msgOperation;
    private final BadWordFiltering badWordFiltering;
    private final RedisService redisService;
    private final Map<Long, ChatListDto> chatParticipantsMap = new HashMap<>();
    private static Long index = 0L;
    private static Long chatRoomId = 1L;

    public ChatDto enterChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("beggar_id", chatDto.getBeggar_id());
        headerAccessor.getSessionAttributes().put("nickName", chatDto.getSender());
        headerAccessor.getSessionAttributes().put("userId", chatDto.getUserId());
        headerAccessor.getSessionAttributes().put("date", chatDto.getDate());
        chatDto.setMessage(chatDto.getSender() + "님이 입장하셨습니다.");

        ChatListDto chatListDto = new ChatListDto(
                chatDto.getBeggar_id(),
                chatDto.getSender(),
                chatDto.getUserId(),
                chatDto.getLevel()
        );
        addChatParticipant(chatListDto);

        MessageType type = MessageType.ENTER;

        Chat chat = Chat.builder()
                .id(index++)
                .sender(chatDto.getSender())
                .message(chatDto.getMessage())
                .level(chatDto.getLevel())
                .beggar_id(chatDto.getBeggar_id())
                .userId(chatDto.getUserId())
                .type(type)
                .image(chatDto.getImage())
                .date(chatDto.getDate())
                .build();
        String chatId = 1+UUID.randomUUID().toString();
        chat.setChatId(chatId);
        redisService.setChatValues(chat, chatDto.getChatRoomId(), chatId);

        return chatDto;
    }

    public void addChatParticipant(ChatListDto participant) {chatParticipantsMap.put(participant.getBeggarId(), participant);}
    public List<ChatListDto> getChatParticipants() {
        return new ArrayList<>(chatParticipantsMap.values());
    }

    public ResponseEntity<ChatRoomDto> enterChatRoomGetChat(User user, Long beggarId) {

        List<Chat> chatList = redisService.getChats(chatRoomId);
        List<ChatDto> chatDtoList = new ArrayList<>();

        for (Chat chat : chatList) {
            ChatDto historyChatDto = ChatDto.fromChat(chat);
            chatDtoList.add(historyChatDto);
        }

        ChatRoomDto chatRoomDto = new ChatRoomDto(chatRoomId, chatDtoList);
        return ResponseEntity.ok(chatRoomDto);
    }

    public ChatDto disconnectChatRoom(SimpMessageHeaderAccessor headerAccessor) {
        String nickName = (String) headerAccessor.getSessionAttributes().get("nickName");
        Long beggar_id = (Long) headerAccessor.getSessionAttributes().get("beggar_id");
        Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");
        String date = (String) headerAccessor.getSessionAttributes().get("date");

        Beggar beggar = beggarCheck(beggar_id);

        ChatDto chatDto = ChatDto.builder()
                .type(MessageType.LEAVE)
                .sender(nickName)
                .beggar_id(beggar_id)
                .level(beggar.getLevel())
                .message(nickName + "님이 퇴장하셨습니다.")
                .userId(userId)
                .chatRoomId(chatRoomId)
                .date(date)
                .build();
        removeChatParticipant(beggar_id);


        Chat chat = Chat.builder()
                .id(index++)
                .sender(chatDto.getSender())
                .message(chatDto.getMessage())
                .level(chatDto.getLevel())
                .beggar_id(chatDto.getBeggar_id())
                .userId(chatDto.getUserId())
                .type(chatDto.getType())
                .image(chatDto.getImage())
                .date(chatDto.getDate())
                .build();
        String chatId = 1+UUID.randomUUID().toString();
        chat.setChatId(chatId);
        redisService.setChatValues(chat, chatDto.getChatRoomId(), chatId);

        return chatDto;
    }

    public void removeChatParticipant(Long beggar_id) {
        chatParticipantsMap.remove(beggar_id);
    }
    
    public void sendChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {
        Beggar beggar = beggarCheck(chatDto.getBeggar_id());
        MessageType type = MessageType.TALK;
        ChatDto newChatDto = badWordFiltering.change(chatDto);

        Chat chat = Chat.builder()
                .id(index++)
                .sender(newChatDto.getSender())
                .message(newChatDto.getMessage())
                .level(newChatDto.getLevel())
                .beggar_id(chatDto.getBeggar_id())
                .userId(chatDto.getUserId())
                .type(type)
                .image(chatDto.getImage())
                .date(chatDto.getDate())
                .build();
        String chatId = 1+UUID.randomUUID().toString();
        chat.setChatId(chatId);
        redisService.setChatValues(chat, chatDto.getChatRoomId(), chatId);
        msgOperation.convertAndSend("/sub/chat/room", newChatDto);
    }

    public Beggar beggarCheck(Long beggar_id) {
        return beggarRepository.findById(beggar_id).orElseThrow(
                () -> new IllegalArgumentException("푸어를 찾을 수 없습니다.")
        );
    }

    public List<ChatImagesDto> saveChatImageList() {
        List<Image> imageList = imageRepository.findAll();
        List<ChatImagesDto> chatImagesList = new ArrayList<>();

        for(Image image : imageList){
            ChatImagesDto imagesDto = ChatImagesDto.builder()
                    .imageId(image.getId())
                    .imageUrl(image.getImageUrl())
                    .build();
            chatImagesList.add(imagesDto);
        }
        return chatImagesList;
    }
}