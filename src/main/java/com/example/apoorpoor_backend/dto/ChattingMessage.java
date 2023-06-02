package com.example.apoorpoor_backend.dto;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ChattingMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private String roomId;
    private String writer;
}