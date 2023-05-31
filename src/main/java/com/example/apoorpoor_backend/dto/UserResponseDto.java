package com.example.apoorpoor_backend.dto;

import com.example.apoorpoor_backend.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String username;
    private Long kakaoId;
    private Long age;
    private String gender;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.kakaoId = user.getKakaoId();
        this.age = user.getAge();
        this.gender = user.getGender();
    }

}
