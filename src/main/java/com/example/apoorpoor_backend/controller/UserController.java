package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.account.RecentSumResponseDto;
import com.example.apoorpoor_backend.dto.account.TotalSumResponseDto;
import com.example.apoorpoor_backend.dto.user.AgeRequestDto;
import com.example.apoorpoor_backend.dto.user.GenderRequestDto;
import com.example.apoorpoor_backend.dto.user.UserResponseDto;
import com.example.apoorpoor_backend.security.UserDetailsImpl;
import com.example.apoorpoor_backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @PutMapping("/age")
    public ResponseEntity<Long> setAge(@Valid @RequestBody AgeRequestDto ageRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long age = ageRequestDto.getAge();
        return userService.setAge(age, userDetails.getUsername());
    }

    @PutMapping("/gender")
    public ResponseEntity<String> setGender(@Valid @RequestBody GenderRequestDto genderRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String gender = genderRequestDto.getGender();
        return userService.setGender(gender, userDetails.getUsername());
    }

    @GetMapping("/mypage")
    public ResponseEntity<UserResponseDto> userInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUsername();
        return userService.userInfo(username);
    }

    @GetMapping("/mypage/status")
    public ResponseEntity<List<TotalSumResponseDto>> getStatus(@AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        return userService.getStatus(username);
    }

    @GetMapping("/mypage/recentStatus")
    public ResponseEntity<RecentSumResponseDto> getRecentStatus(@AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        return userService.getRecentStatus(username);
    }
}