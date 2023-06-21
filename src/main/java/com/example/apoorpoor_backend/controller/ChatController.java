package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.chat.BadWordFiltering;
import com.example.apoorpoor_backend.dto.chat.ChatDto;
import com.example.apoorpoor_backend.dto.chat.ChatListDto;
import com.example.apoorpoor_backend.service.ChatService;
import com.example.apoorpoor_backend.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate msgOperation;
    private final S3Uploader s3Uploader;
    private final BadWordFiltering badWordFiltering;


    @MessageMapping("/chat/enter")
    @SendTo("/sub/chat/room")
    public void enterChatRoom(@RequestBody ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        Thread.sleep(500);
        ChatDto newchatdto = chatService.enterChatRoom(chatDto, headerAccessor);
        msgOperation.convertAndSend("/sub/chat/room", newchatdto);
    }

    @GetMapping("/chat/list")
    public List<ChatListDto> getChatList() {
        return chatService.getChatParticipants();
    }

    @MessageMapping("/chat/send")
    public ChatDto sendChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        Thread.sleep(500);
        ChatDto newChatDto = badWordFiltering.change(chatDto);
        chatService.sendChatRoom(newChatDto, headerAccessor);
        return newChatDto;
    }

    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        ChatDto chatDto = chatService.disconnectChatRoom(headerAccessor);
        msgOperation.convertAndSend("/sub/chat/room", chatDto);
    }


    @ResponseBody
    @PostMapping(value = "/chat/image",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadImage(@RequestParam(value = "image", required = false) MultipartFile image, @AuthenticationPrincipal UserDetails userDetails)throws IOException{
        return s3Uploader.uploadImage(image);
    }
}
