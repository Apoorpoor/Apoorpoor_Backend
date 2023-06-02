package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.KakaoUserInfoDto;
import com.example.apoorpoor_backend.dto.TokenDto;
import com.example.apoorpoor_backend.jwt.JwtUtil;
import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.RefreshToken;
import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.model.UserRoleEnum;
import com.example.apoorpoor_backend.repository.BeggarRepository;
import com.example.apoorpoor_backend.repository.RefreshTokenRepository;
import com.example.apoorpoor_backend.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BeggarRepository beggarRepository;
    private final JwtUtil jwtUtil;

    public String kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {

        TokenDto tokenDto = getToken(code);

        String access_token = tokenDto.getAccessToken();
        String refresh_token = tokenDto.getRefreshToken();

        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(access_token);

        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);

        TokenDto tokenDto2 = jwtUtil.creatAllToken(kakaoUser.getUsername(), kakaoUser.getRole());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUsername(kakaoUser.getUsername());

        if(refreshToken.isPresent()){
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto2.getRefreshToken()));
        } else{
            RefreshToken newToken = new RefreshToken(tokenDto2.getRefreshToken(), kakaoUser.getUsername());
            refreshTokenRepository.save(newToken);
        }

        response.addHeader(JwtUtil.ACCESS_KEY, tokenDto2.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_KEY, tokenDto2.getRefreshToken());

        // 거지 불러오기
        Optional<Beggar> findBeggar = beggarRepository.findByUserId(kakaoUser.getId());

        String nickname_flag = "false";
        if (findBeggar.isPresent()) nickname_flag = "true";

        response.addHeader("NICKNAME_FLAG", nickname_flag);

        return "로그인 성공";
    }

    private TokenDto getToken(String code) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "b285dc18b3ecd8e4bc2dcc9a9460a12d");
        body.add("client_secret", "3AvD4HWtT4ZrHjrd9pC9TcyyjGtBKNQV");
        body.add("redirect_uri", "http://localhost:3000/oauth/kakao");
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return new TokenDto(jsonNode.get("access_token").asText(), jsonNode.get("refresh_token").asText());
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        System.out.println("jsonNode = " + jsonNode);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();

        return new KakaoUserInfoDto(id, nickname);
    }

    @Transactional
    public User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        Long kakaoId = kakaoUserInfo.getId();
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);
        if (kakaoUser == null) {

            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);
            kakaoUser = new User("kakao"+kakaoId, encodedPassword, UserRoleEnum.USER, kakaoId);

            userRepository.save(kakaoUser);
            kakaoUser = kakaoUser.kakaoIdUpdate(kakaoId);
        }
        return kakaoUser;
    }
}