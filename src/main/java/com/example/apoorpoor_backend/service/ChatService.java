package com.example.apoorpoor_backend.service;


import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.apoorpoor_backend.dto.ChatDto;
import com.example.apoorpoor_backend.entity.Chat;
import com.example.apoorpoor_backend.entity.ChatRoom;
import com.example.apoorpoor_backend.entity.MessageType;
import com.example.apoorpoor_backend.entity.User;
import com.example.apoorpoor_backend.repository.ChatRepository;
import com.example.apoorpoor_backend.repository.ChatRoomRepository;
import com.example.apoorpoor_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private static final String S3_BUCKET_PREFIX = "S3";

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;

    public ChatDto enterChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor){
        User user = userIDCheck(chatDto.getUserId());
        ChatRoom room = roomIdCheck(chatDto.getRoomId());
        user.enterRoom(room);

        chatDto.setMessage(chatDto.getSender() + "님 입장!! ο(=•ω＜=)ρ⌒☆");

        Long headCount = userRepository.countAllByRoom_Id(chatRoom.getId());
        chatRoom.updateCount(headCount);
        return chatDto;
    }
    public ChatDto disconnectChatRoom(SimpMessageHeaderAccessor headerAccessor) {
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");
        String nickName = (String) headerAccessor.getSessionAttributes().get("nickName");
        String userId = (String) headerAccessor.getSessionAttributes().get("userId");
        User user = userNameCheck(nickName);
        ChatRoom room = roomIdCheck(roomId);
        user.exitRoom(room);

        ChatDto chatDto = ChatDto.builder()
                .type(MessageType.LEAVE)
                .userId(userId)
                .roomId(roomId)
                .sender(nickName)
                .userId(userId)
                .message(nickName + "님 퇴장!! ヽ(*。>Д<)o゜")
                .build();

        Long headCount = userRepository.countAllByRoom_Id(room.getId());
        room.updateCount(headCount);
        if(headCount == 0){
            chatRoomRepository.deleteByRoomId(roomId);
        }

        return chatDto;
    }
    public ChatRoom roomIdCheck(String roomId) {
        return chatRoomRepository.findByRoomId(roomId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 채팅방입니다.")
        );
    }

    public User userNameCheck(String userName) {
        return userRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
    }

    public User userIDCheck(String userId) {
        return userRepository.findByUserid(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
    }
    public void sendChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {

        ChatRoom room = roomIdCheck(chatDto.getRoomId());
        User user = userIDCheck(chatDto.getUserId());
        String profile_image = chatDto.getProfile_image();
        MessageType type = MessageType.TALK;

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateformat = format.format(date);
        chatDto.setDate(dateformat);
        chatDto.setProfile_image(user.getProfile_image());

        Chat chat = new Chat(chatDto, room, user, type, profile_image);
        chatRepository.save(chat);
    }

    public String uploadImage(MultipartFile image) throws IOException {
        String image_url = "이미지 업로드 실패";

        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        int millis = now.get(ChronoField.MILLI_OF_SECOND);

        if(image != null){
            String newFileName = "image"+hour+minute+second+millis;
            String fileExtension = '.'+image.getOriginalFilename().replaceAll("^.*\\.(.*)$", "$1");
            String imageName = S3_BUCKET_PREFIX + newFileName + fileExtension;

            String[] extensionArray = {".png", ".jpeg", ".jpg", ".webp", ".gif"};

            List<String> extensionList = new ArrayList<>(Arrays.asList(extensionArray));

            if(!extensionList.contains(fileExtension)){
                throw new ApiException(ExceptionEnum.UNAUTHORIZED_FILE);
            }

            if(image.getSize() > 20971520){
                throw new ApiException(ExceptionEnum.MAX_FILE_SIZE);
            }

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(image.getContentType());
            objectMetadata.setContentLength(image.getSize());

            InputStream inputStream = image.getInputStream();

            amazonS3.putObject(new PutObjectRequest(bucketName, imageName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            image_url = amazonS3.getUrl(bucketName, imageName).toString();
        }
        return image_url;
    }

}
