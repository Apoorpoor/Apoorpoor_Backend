package com.example.apoorpoor_backend.model;

import com.example.apoorpoor_backend.dto.chat.ChatDto;
import com.example.apoorpoor_backend.model.enumType.MessageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;

    private String message;

    @ColumnDefault("1")
    @Column(nullable = false)
    private Long level;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beggar_id")
    private Beggar beggar;

//    @Column(nullable = false)
//    @ColumnDefault("0")
//    private long likeCount;


    public Chat (ChatDto chatDto, Beggar beggar, MessageType type) {
        this.sender = chatDto.getSender();
        this.message = chatDto.getMessage();
        this.beggar = beggar;
        this.type = type;
        this.level = 1L;
//        this.likeCount = 0;
    }

//    public void setLikes(){
//        ++likeCount;
//    }
}
