package com.example.apoorpoor_backend.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.apoorpoor_backend.dto.ChatDto;
import com.example.apoorpoor_backend.entity.Beggar;
import com.example.apoorpoor_backend.entity.Chat;
import com.example.apoorpoor_backend.entity.ChatRoom;
import com.example.apoorpoor_backend.entity.MessageType;
import com.example.apoorpoor_backend.repository.ChatRepository;
import com.example.apoorpoor_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional


public class ChatService {
    private static final String S3_BUCKET_PREFIX = "S3";

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final ChatRepository chatRepository;
    private final AmazonS3 amazonS3;
    private final UserRepository userRepository;


    public ChatDto enterChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("nickname", chatDto.getSender());
        headerAccessor.getSessionAttributes().put("roomId", chatDto.getRoomId());
        headerAccessor.getSessionAttributes().put("beggarId", chatDto.getBeggar_id());

        Beggar begger = beggarCheck(chatDto.getBeggar_id());

        ChatRoom room = roomIdCheck(chatDto.getRoomId());
        begger.enterRoom(room);

        chatDto.setMessage(chatDto.getSender() + "님이 참여하셨습니다.");
        return chatDto;
    }

    private ChatRoom roomIdCheck(String roomId) {
        return chatRepository.findByRoomId(roomId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 채팅방입니다.")
        );
    }

    public Beggar beggarCheck(Long beggarId){
        return userRepository.findByBeggarId(beggarId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 거지입니다.")
        );
    }

    public void sendChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {

        Beggar beggar = beggarCheck(chatDto.getBeggar_id());
        MessageType type = MessageType.TALK;

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateformat = format.format(date);
        chatDto.setDate(dateformat);

        Chat chat = new Chat(chatDto, beggar,type);
        chatRepository.save(chat);

    }

    public ChatDto disconnectChatRoom(StompHeaderAccessor headerAccessor) {
        String nickName = (String) headerAccessor.getSessionAttributes().get("nickname");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");
        String beggarId = (String) headerAccessor.getSessionAttributes().get("beggarId");

        ChatDto chatDto = ChatDto.builder().type(MessageType.LEAVE).sender(nickName).message(nickName + "님이 나가셨습니다.").build();
        return chatDto;
    }

    public String uploadImage(MultipartFile image) throws IOException {
        String image_url ="이미지 업로드 실패";

        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getMinute();
        int millis = now.get(ChronoField.MILLI_OF_SECOND);

        if(image != null){
            String newFileName = "image"+hour+minute+second+millis;
            String fileExtension = '.'+ image.getOriginalFilename().replaceAll("^.*\\.(.*)$", "$1");
            String imageName = S3_BUCKET_PREFIX + newFileName + fileExtension;

            String[] extensionArray = {".png", ".jpeg", ".jpg", ".webp", ".gif"};

            List<String> extensionList = new ArrayList<>(Arrays.asList(extensionArray));

            if(!extensionList.contains(fileExtension)){
                throw new IllegalStateException("확장자는 .png, .jpeg, .jpg, .webp, .gif만 가능합니다.");
            }

            if(image.getSize() > 20971520){
                throw new IllegalStateException("파일 크기는 최대 20MB입니다.");
            }

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(image.getContentType());
            objectMetadata.setContentLength(image.getSize());

            InputStream inputStream = image.getInputStream();

            amazonS3.putObject(new PutObjectRequest(bucket, imageName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            image_url = amazonS3.getUrl(bucket, imageName).toString();
        }
        return image_url;
    }
}