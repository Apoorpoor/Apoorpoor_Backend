package com.example.apoorpoor_backend.dto;

import com.example.apoorpoor_backend.model.Beggar;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BeggarResponseDto {
    private Long beggar_id;
    private Long user_id;
    private String nickname;
    private Long point;
    private Long level;
    private String mention;

    public BeggarResponseDto(Beggar beggar){
        this.beggar_id = beggar.getId();
        this.user_id = beggar.getUser().getId();
        this.nickname = beggar.getNickname();
        this.level = beggar.getLevel();
        this.point = beggar.getPoint();
        this.mention = beggar.getMention();
    }
}
