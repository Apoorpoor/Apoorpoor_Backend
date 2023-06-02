package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.model.ChatMessage;
import com.example.apoorpoor_backend.service.S3Uploader;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final S3Uploader s3Uploader;

    @MessageMapping("/chat/enter")
    @SendTo("/sub/chat/room")
    public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @MessageMapping("/chat/send")
    @SendTo("/sub/chat/room")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }


    @ResponseBody
    @PostMapping(value = "/chat/image",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadImage(@RequestParam(value = "image", required = false)MultipartFile image, @AuthenticationPrincipal UserDetails userDetails)throws IOException{
        String image_url = s3Uploader.uploadImage(image);
        return image_url;
        //return chatService.uploadImage(image);
    }
}