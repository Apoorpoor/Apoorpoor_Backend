package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.AgeRequestDto;
import com.example.apoorpoor_backend.dto.GenderRequestDto;
import com.example.apoorpoor_backend.dto.UserResponseDto;
import com.example.apoorpoor_backend.security.UserDetailsImpl;
import com.example.apoorpoor_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @PutMapping("/age")
    public ResponseEntity<Long> setAge(@RequestBody AgeRequestDto ageRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long age = ageRequestDto.getAge();
        return userService.setAge(age, userDetails.getUsername());
    }

    @PutMapping("/gender")
    public ResponseEntity<String> setGender(@RequestBody GenderRequestDto genderRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String gender = genderRequestDto.getGender();
        return userService.setGender(gender, userDetails.getUsername());
    }

    @GetMapping("/mypage")
    public ResponseEntity<UserResponseDto> userInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUsername();
        return userService.userInfo(username);
    }
}
