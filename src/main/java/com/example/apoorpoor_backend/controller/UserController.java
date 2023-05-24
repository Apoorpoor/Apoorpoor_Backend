package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.user.dto.UserLogOutDto;
import com.example.apoorpoor_backend.user.dto.UserSignUpDto;
import com.example.apoorpoor_backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return new ResponseEntity<>("회원가입 완료", HttpStatus.OK);
    }

    @GetMapping("/jwt-test")
    public ResponseEntity<String> jwtTest() {
        return new ResponseEntity<>("jwtTest 요청 성공", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logOut(@RequestBody UserLogOutDto userLogOutDto) {
        userService.logOut(userLogOutDto);
        return new ResponseEntity<>("로그아웃 완료", HttpStatus.OK);
    }
}
