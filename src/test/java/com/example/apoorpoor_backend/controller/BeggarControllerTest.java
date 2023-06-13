package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.beggar.BeggarRequestDto;
import com.example.apoorpoor_backend.dto.beggar.BeggarResponseDto;
import com.example.apoorpoor_backend.dto.beggar.BeggarSearchResponseDto;
import com.example.apoorpoor_backend.dto.common.StatusResponseDto;
import com.example.apoorpoor_backend.model.Badge;
import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.model.enumType.UserRoleEnum;
import com.example.apoorpoor_backend.security.UserDetailsImpl;
import com.example.apoorpoor_backend.service.BeggarService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class BeggarControllerTest {
    private static UserDetailsImpl user;

    @BeforeAll
    static void setUp() {
        String username = "user";
        UserRoleEnum userRoleEnum = UserRoleEnum.USER;
        Long kakaoId = 213213124L;
        User user1 = new User(username, null, userRoleEnum, kakaoId);
        user = new UserDetailsImpl(user1, user1.getUsername());
    }

    @BeforeEach
    void setAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
    }

    @Test
    @DisplayName("createBeggar Test")
    void createBeggar() {
        // Mock 주입
        BeggarService mockedBeggarService = mock(BeggarService.class);
        BeggarRequestDto mockedRequestDto = new BeggarRequestDto();
        UserDetailsImpl mockedUserDetails = new UserDetailsImpl(user.getUser(), user.getUsername());

        // service의 메서드가 호출될때 임의의 파라미터로 응답 확인
        StatusResponseDto expectedResponse = new StatusResponseDto("Success");
        when(mockedBeggarService.createBeggar(eq(mockedRequestDto), anyString())).thenReturn(ResponseEntity.ok(expectedResponse));

        // cotroller 인스턴스 생성
        BeggarController controller = new BeggarController(mockedBeggarService);

        // controller 메서드 호출
        ResponseEntity<StatusResponseDto> response = controller.createBeggar(mockedRequestDto, mockedUserDetails);

        // 응답 검증
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());

        // Mock 객체로 서비스 메서드의 호출 확인
        verify(mockedBeggarService).createBeggar(eq(mockedRequestDto), anyString());
    }

    @Test
    @DisplayName("myBeggar Test")
    void myBeggar() {
        // Mock 객체 생성
        BeggarService mockedBeggarService = mock(BeggarService.class);
        UserDetailsImpl mockedUserDetails = new UserDetailsImpl(user.getUser(), user.getUsername());

        BeggarSearchResponseDto expectedResponseDto = BeggarSearchResponseDto.builder()
                .beggarId(1L)
                .userId(2L)
                .nickname("John Doe")
                .point(100L)
                .exp(500L)
                .badgeList(List.of(new Badge(1L, "badge1", "badgeImage1"), new Badge(2L, "badge2", "badgeImage2")))
                .level(5L)
                .description("Description")
                .gender("Male")
                .age(30L)
                .topImage("topImage.jpg")
                .bottomImage("bottomImage.jpg")
                .shoesImage("shoesImage.jpg")
                .accImage("accImage.jpg")
                .customImage("customImage.jpg")
                .build();

        //테스트 예상 결과
        ResponseEntity<BeggarSearchResponseDto> expectedResponse = new ResponseEntity<>(expectedResponseDto, HttpStatus.OK);

        //컨트롤러 인스턴스 생성
        BeggarController mockedBeggarController = new BeggarController(mockedBeggarService);

        // 임의의 파라미터로 응답 확인
        Mockito.when(mockedBeggarService.myBeggar(Mockito.anyString())).thenReturn(expectedResponse);

        // 응답객체 저장
        ResponseEntity<BeggarSearchResponseDto> response = mockedBeggarController.myBeggar(mockedUserDetails);

        // 실제 응답과 예상응답 비교
        assertEquals(expectedResponse, response);
    }

//    @Test
//    @DisplayName("getUserBeggar")
//    void getUserBeggar() {
//        // Mock 객체 생성
//        String nickname = "tester";
//
//        BeggarService mockedBeggarService = mock(BeggarService.class);
//        UserDetailsImpl mockedUserDetails = new UserDetailsImpl(user.getUser(), user.getUsername());
//
//        BeggarRequestDto mockedRequestDto = new BeggarRequestDto();
//
//        BeggarResponseDto expectedResponseDto = BeggarResponseDto.builder()
//                .beggar_id(1L)
//                .user_id(1L)
//                .description("testDescription")
//                .level(1L)
//                .nickname(nickname)
//                .point(100L)
//                .build();
//
//        // 테스트 예상 결과
//        ResponseEntity<BeggarResponseDto> expectedResponse = new ResponseEntity<>(expectedResponseDto, HttpStatus.OK);
//
//        // 컨트롤러 인스턴스 생성
//        BeggarController mockedBeggarController = new BeggarController(mockedBeggarService);
//
//        // 임의의 파라미터로 응답 확인
//        Mockito.when(mockedBeggarService.getUserBeggar(Mockito.anyLong())).thenReturn(expectedResponse);
//
//        // 응답 객체 저장
//        ResponseEntity<BeggarResponseDto> response = mockedBeggarController.getUserBeggar(anyLong());
//
//        // 테스트 결과 검증
//        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
//        assertEquals(expectedResponse.getBody(), response.getBody());
//    }
}