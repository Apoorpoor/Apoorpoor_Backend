package com.example.apoorpoor_backend.dto.user;

import com.example.apoorpoor_backend.model.User;
import lombok.Builder;
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

    public UserResponseDto(Long id, String username, Long kakaoId, Long age, String gender) {
        this.id = id;
        this.username = username;
        this.kakaoId = kakaoId;
        this.age = age;
        this.gender = gender;
    }
}
