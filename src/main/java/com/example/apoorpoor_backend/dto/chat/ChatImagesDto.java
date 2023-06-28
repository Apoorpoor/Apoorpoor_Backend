package com.example.apoorpoor_backend.dto.chat;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class ChatImagesDto {
    private Long imageId;
    private String imageUrl;
}
