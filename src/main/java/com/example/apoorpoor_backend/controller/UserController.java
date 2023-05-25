package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.UserLogOutDto;
import com.example.apoorpoor_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

//    @PostMapping("/sign-up")
//    public ResponseEntity<String> signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
//        userService.signUp(userSignUpDto);
//        return new ResponseEntity<>("회원가입 완료", HttpStatus.OK);
//    }

    @GetMapping("/jwt-test")
    public ResponseEntity<String> jwtTest() {
        return new ResponseEntity<>("jwtTest 요청 성공", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logOut(@RequestBody UserLogOutDto userLogOutDto) {
        userService.logOut(userLogOutDto);
        return new ResponseEntity<>("로그아웃 완료", HttpStatus.OK);
    }

    // 로그인 한 유저가 메인페이지를 요청할 때 유저의 이름 반환
    @GetMapping("/user-info")
    public String getUserName(Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return userService.getBeggarName(user.getUsername());
    }
}
