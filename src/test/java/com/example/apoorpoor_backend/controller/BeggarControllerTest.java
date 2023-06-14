package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.beggar.BeggarRequestDto;
import com.example.apoorpoor_backend.dto.beggar.BeggarResponseDto;
import com.example.apoorpoor_backend.dto.beggar.BeggarSearchResponseDto;
import com.example.apoorpoor_backend.dto.common.StatusResponseDto;
import com.example.apoorpoor_backend.model.Badge;
import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.model.enumType.UserRoleEnum;
import com.example.apoorpoor_backend.security.UserDetailsImpl;
import com.example.apoorpoor_backend.service.BeggarService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
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
        StatusResponseDto mockedResponseDto = new StatusResponseDto("푸어가  생성 되었어요...");

        // service 의 메서드가 호출될때 임의의 파라미터로 응답 확인
        ResponseEntity<StatusResponseDto> expectedResponse = new ResponseEntity<>(mockedResponseDto, HttpStatus.OK);
        when(mockedBeggarService.createBeggar(any(), anyString())).thenReturn(expectedResponse);

        // controller 인스턴스 생성
        BeggarController controller = new BeggarController(mockedBeggarService);

        // controller 메서드 호출
        ResponseEntity<StatusResponseDto> response = controller.createBeggar(mockedRequestDto, mockedUserDetails);

        // 응답 검증
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(expectedResponse, response);

        // Mock 객체로 서비스 메서드의 호출 확인
        verify(mockedBeggarService).createBeggar(any(), anyString());
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
                .nickname("Tester")
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
        when(mockedBeggarService.myBeggar(anyString())).thenReturn(expectedResponse);

        // 응답객체 저장
        ResponseEntity<BeggarSearchResponseDto> response = mockedBeggarController.myBeggar(mockedUserDetails);

        // 실제 응답과 예상응답 비교
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(expectedResponse, response);

        // Mock 객체로 서비스 메서드의 호출 확인
        verify(mockedBeggarService).myBeggar(anyString());
    }

    @Test
    @DisplayName("getUserBeggar Test")
    void getUserBeggar() {
        // Mock 객체 생성
        BeggarService mockedBeggarService = mock(BeggarService.class);

        BeggarSearchResponseDto expectedResponseDto = BeggarSearchResponseDto.builder()
                .beggarId(1L)
                .userId(2L)
                .nickname("Tester")
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

        // 테스트 예상 결과
        ResponseEntity<BeggarSearchResponseDto> expectedResponse = new ResponseEntity<>(expectedResponseDto, HttpStatus.OK);

        // 컨트롤러 인스턴스 생성
        BeggarController mockedBeggarController = new BeggarController(mockedBeggarService);

        // 임의의 파라미터로 응답 확인
        when(mockedBeggarService.getUserBeggar(anyLong())).thenReturn(expectedResponse);

        // 응답 객체 저장
        ResponseEntity<BeggarSearchResponseDto> response = mockedBeggarController.getUserBeggar(anyLong());

        // 테스트 결과 검증
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(expectedResponse, response);

        // Mock 객체로 서비스 메서드의 호출 확인
        verify(mockedBeggarService).getUserBeggar(anyLong());
    }

//    @Test
//    @DisplayName("updateBeggar Test")
//    void updateBeggar() {
//        // Mock 객체 생성
//        BeggarService mockedBeggarService = mock(BeggarService.class);
//        UserDetailsImpl mockedUserDetails = new UserDetailsImpl(user.getUser(), user.getUsername());
//
//        BeggarResponseDto expectedResponseDto = BeggarResponseDto.builder()
//                .beggar_id(1L)
//                .user_id(1L)
//                .point(10L)
//                .nickname("Tester")
//                .level(3L)
//                .description("CommentTest")
//                .build();
//
//        // Test 예상 결과
//        ResponseEntity<BeggarResponseDto> expectedResponse = new ResponseEntity<>(expectedResponseDto, HttpStatus.OK);
//
//        // 컨트롤러 인스턴스 생성
//        BeggarController mockedBeggarController = new BeggarController(mockedBeggarService);
//
//        // when()을 사용하여 메서드 호출에 대한 응답을 지정
//        when(mockedBeggarService.updateBeggar(any(), anyString())).thenReturn(expectedResponse);
//
//        // 응답 객체 저장
//        ResponseEntity<BeggarResponseDto> response = mockedBeggarController.updateBeggar(any(), mockedUserDetails);
//
//        // 테스트 결과 검증
//        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
//        assertEquals(expectedResponse.getBody(), response.getBody());
//
//        // Mock 객체로 서비스 메서드의 호출 확인
//        verify(mockedBeggarService).updateBeggar(any(), anyString());
//    }
}