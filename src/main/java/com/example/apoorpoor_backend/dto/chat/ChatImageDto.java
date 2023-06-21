package com.example.apoorpoor_backend.dto.chat;

import com.example.apoorpoor_backend.model.Image;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChatImageDto {
    private Long imageId;

    public String imageUrl;

    private List<Image> imageList;
}
