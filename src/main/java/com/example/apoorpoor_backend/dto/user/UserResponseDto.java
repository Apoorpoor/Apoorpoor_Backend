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
    private Long age;
    private String gender;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.age = user.getAge();
        this.gender = user.getGender();
    }

    public UserResponseDto(Long id, String username, Long age, String gender) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.gender = gender;
    }
}
