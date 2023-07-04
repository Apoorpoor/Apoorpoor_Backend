package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.account.RecentSumResponseDto;
import com.example.apoorpoor_backend.dto.account.TotalSumResponseDto;
import com.example.apoorpoor_backend.dto.user.AgeRequestDto;
import com.example.apoorpoor_backend.dto.user.GenderRequestDto;
import com.example.apoorpoor_backend.dto.user.UserResponseDto;
import com.example.apoorpoor_backend.security.UserDetailsImpl;
import com.example.apoorpoor_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "UserController", description = "유저 controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "나이 설정 API" , description = "나이 설정")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "나이 설정 완료" )})
    @PutMapping("/age")
    public ResponseEntity<Long> setAge(@Valid @RequestBody AgeRequestDto ageRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long age = ageRequestDto.getAge();
        return userService.setAge(age, userDetails.getUsername());
    }

    @Operation(summary = "성별 설정 API" , description = "성별 설정")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "성별 설정 완료" )})
    @PutMapping("/gender")
    public ResponseEntity<String> setGender(@Valid @RequestBody GenderRequestDto genderRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String gender = genderRequestDto.getGender();
        return userService.setGender(gender, userDetails.getUsername());
    }

    @Operation(summary = "마이페이지 조회 API" , description = "마이페이지 조회")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "마이페이지 조회 완료" )})
    @GetMapping("/mypage")
    public ResponseEntity<UserResponseDto> userInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUsername();
        return userService.userInfo(username);
    }

    @Operation(summary = "전체 카테고리 소비성향 조회 API" , description = "전체 카테고리 소비성향 조회")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "전체 카테고리 소비성향 조회 완료" )})
    @GetMapping("/mypage/status")
    public ResponseEntity<List<TotalSumResponseDto>> getStatus(@AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        return userService.getStatus(username);
    }

    @Operation(summary = "최근 6개월 소비/수입근황 조회 API" , description = "최근 6개월 소비/수입근황 조회")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "최근 6개월 소비/수입근황 조회 완료" )})
    @GetMapping("/mypage/recentStatus")
    public ResponseEntity<RecentSumResponseDto> getRecentStatus(@AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        return userService.getRecentStatus(username);
    }
}