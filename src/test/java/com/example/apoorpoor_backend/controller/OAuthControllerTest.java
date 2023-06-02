package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.service.KakaoService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OAuthControllerTest {

    @Mock
    private KakaoService kakaoService;

    @InjectMocks
    private OAuthController oAuthController;

    @DisplayName("카카오 로그인 테스트")
    @Test
    void kakaoLoginTest() throws Exception {
        //given
        String code = "testcode";
        HttpServletResponse response = mock(HttpServletResponse.class);
        String redirectUrl = "http://localhost:3000/oauth/kakao";

        when(kakaoService.kakaoLogin(code, response)).thenReturn(redirectUrl);

        //when
        String result = oAuthController.kakaoLogin(code, response);

        //then
        assertEquals(redirectUrl, result);

        verify(kakaoService, times(1)).kakaoLogin(code, response);
    }

}