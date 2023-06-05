//package com.example.apoorpoor_backend.controller;
//
//import com.example.apoorpoor_backend.dto.StatusResponseDto;
//import com.example.apoorpoor_backend.dto.beggar.BeggarRequestDto;
//import com.example.apoorpoor_backend.model.User;
//import com.example.apoorpoor_backend.model.UserRoleEnum;
//import com.example.apoorpoor_backend.security.UserDetailsImpl;
//import com.example.apoorpoor_backend.service.BeggarService;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//class BeggarControllerTest {
//
//    private static UserDetailsImpl user;
//
//    @BeforeAll
//    static void setUp() {
//        String username = "user";
//        UserRoleEnum userRoleEnum = UserRoleEnum.USER;
//        Long kakaoId = 213213124L;
//        User user1 = new User(username, null, userRoleEnum, kakaoId);
//        user = new UserDetailsImpl(user1, user1.getUsername());
//    }
//
//    @BeforeEach
//    void setAuthentication() {
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
//    }
//
//    @Test
//    @DisplayName("createBeggar Test")
//    void createBeggar() {
//        // Mock 주입
//        BeggarService beggarService = mock(BeggarService.class);
//        BeggarRequestDto requestDto = new BeggarRequestDto();
//        UserDetailsImpl userDetails = new UserDetailsImpl(user.getUser(), user.getUsername());
//
//        // service의 메서드가 호출될때 임의의 파라미터로 응답 확인
//        StatusResponseDto expectedResponse = new StatusResponseDto("Success");
//        when(beggarService.createBeggar(eq(requestDto), anyString())).thenReturn(ResponseEntity.ok(expectedResponse));
//
//        // cotroller 인스턴스 생성
//        BeggarController controller = new BeggarController(beggarService);
//
//        // controller 메서드 호출
//        ResponseEntity<StatusResponseDto> response = controller.createBeggar(requestDto, userDetails);
//
//        // 응답 검증
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(expectedResponse, response.getBody());
//
//        // Mock 객체로 서비스 메서드의 호출 확인
//        verify(beggarService).createBeggar(eq(requestDto), anyString());
//    }
//
//    @Test
//    void findBeggar() {
//    }
//
//    @Test
//    void updateBeggar() {
//    }
//}