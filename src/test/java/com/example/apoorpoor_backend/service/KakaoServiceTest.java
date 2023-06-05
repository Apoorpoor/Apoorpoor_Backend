//package com.example.apoorpoor_backend.service;
//
//import com.example.apoorpoor_backend.dto.KakaoUserInfoDto;
//import com.example.apoorpoor_backend.dto.TokenDto;
//import com.example.apoorpoor_backend.jwt.JwtUtil;
//import com.example.apoorpoor_backend.model.RefreshToken;
//import com.example.apoorpoor_backend.model.User;
//import com.example.apoorpoor_backend.model.UserRoleEnum;
//import com.example.apoorpoor_backend.repository.BeggarRepository;
//import com.example.apoorpoor_backend.repository.RefreshTokenRepository;
//import com.example.apoorpoor_backend.repository.UserRepository;
//import jakarta.servlet.http.HttpServletResponse;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import static org.mockito.Mockito.*;
//
//class KakaoServiceTest {
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private RefreshTokenRepository refreshTokenRepository;
//
//    @Mock
//    private BeggarRepository beggarRepository;
//
//    @Mock
//    private JwtUtil jwtUtil;
//
//    @InjectMocks
//    private KakaoService kakaoService;
//
//    @DisplayName("카카오 로그인 테스트")
//    @Test
//    void kakaoLoginTest() throws Exception {
//        // given
//        String code = "testcode";
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        TokenDto tokenDto = new TokenDto("access_token", "refresh_token");
//        KakaoUserInfoDto kakaoUserInfoDto = new KakaoUserInfoDto(123456789L, "testuser");
//        User kakaoUser = new User("kakaoid123456789", "encodedPassword", UserRoleEnum.USER, 123456789L);
//        TokenDto generatedTokenDto = new TokenDto("generated_access_token", "generated_refresh_token");
//        RefreshToken existingRefreshToken = new RefreshToken("existing_refresh_token", "kakaoid123456789");
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(JwtUtil.ACCESS_KEY, "generated_access_token");
//        headers.add(JwtUtil.REFRESH_KEY, "generated_refresh_token");
//        headers.add("NICKNAME_FLAG", "true");
//
////        when(kakaoService.getToken(code)).thenReturn(tokenDto);
////        when(kakaoService.getKakaoUserInfo("access_token")).thenReturn(kakaoUserInfoDto);
////        when(userRepository.findByKakaoId(123456789L)).thenReturn(Optional.empty());
////        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
////        when(userRepository.save(any(User.class))).thenReturn(kakaoUser);
////        when(jwtUtil.creatAllToken("kakaoid123456789", UserRoleEnum.USER)).thenReturn(generatedTokenDto);
////        when(refreshTokenRepository.findByUsername("kakaoid123456789")).thenReturn(Optional.of(existingRefreshToken));
////
////        // when
////        String result = kakaoService.kakaoLogin(code, response);
////
////        // then
////        assertEquals("로그인 성공", result);
////
////        verify(kakaoService, times(1)).getToken(code);
////        verify(kakaoService, times(1)).getKakaoUserInfo("access_token");
////        verify(userRepository, times(1)).findByKakaoId(123456789L);
////        verify(passwordEncoder, times(1)).encode(anyString());
////        verify(userRepository, times(1)).save(any(User.class));
////        verify(jwtUtil, times(1)).creatAllToken("kakaoid123456789", UserRoleEnum.USER);
////        verify(refreshTokenRepository, times(1)).findByUsername("kakaoid123456789");
////        verify(refreshTokenRepository, times(1)).save(existingRefreshToken.updateToken("generated_refresh_token"));
////        verify(response, times(1)).addHeader(JwtUtil.ACCESS_KEY, "generated_access_token");
////        verify(response, times(1)).addHeader(JwtUtil.REFRESH_KEY, "generated_refresh_token");
////        verify(response, times(1)).addHeader("NICKNAME_FLAG", "true");
//    }
//
//}