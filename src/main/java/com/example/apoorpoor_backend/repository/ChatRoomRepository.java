package com.example.apoorpoor_backend.repository;

import com.example.apoorpoor_backend.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByRoomId(String roomId);

    Optional<ChatRoom> findByHostAndRoomName(String host, String roomName);
    void deleteByRoomId(String roomId);

}
