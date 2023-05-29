package com.example.apoorpoor_backend.config.oauth.handler;

import com.example.apoorpoor_backend.dto.TokenDto;
import com.example.apoorpoor_backend.jwt.JwtUtil;
import com.example.apoorpoor_backend.model.RefreshToken;
import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.repository.RefreshTokenRepository;
import com.example.apoorpoor_backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;

    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        String username = extractUsername(authentication); // 인증 정보에서 Username(email) 추출
        TokenDto tokenDto = jwtUtil.createAllToken(username);//ateAccessToken(email); // JwtService의 createAccessToken을 사용하여 AccessToken 발급
        //String refreshToken = jwtUtil.createRefreshToken(); // JwtService의 createRefreshToken을 사용하여 RefreshToken 발급
        String accessToken = tokenDto.getAccessToken();
        String newRefreshToken = tokenDto.getRefreshToken();
        //jwtUtil.sendAccessAndRefreshToken(response, accessToken, refreshToken); // 응답 헤더에 AccessToken, RefreshToken 실어서 응답
        jwtUtil.setHeaderAccessToken(response, accessToken);
        jwtUtil.setHeaderRefreshToken(response, newRefreshToken);

        //userRepository.findByUsername(username)

        refreshTokenRepository.findByUsername(username)
                .ifPresent(refresh -> {
                    refresh.updateToken(newRefreshToken);
                    refreshTokenRepository.saveAndFlush(refresh);
                });
        Optional<RefreshToken> findRefreshToken = refreshTokenRepository.findByUsername(username);
        if(!findRefreshToken.isPresent()) {
            RefreshToken refreshToken = new RefreshToken(newRefreshToken, username);
            refreshTokenRepository.save(refreshToken);
        }
        log.info("로그인에 성공하였습니다. username : {}", username);
        log.info("로그인에 성공하였습니다. AccessToken : {}", accessToken);
        log.info("로그인에 성공하였습니다. RefreshToken: {}", newRefreshToken);
        log.info("발급된 AccessToken 만료 기간 : {}", accessTokenExpiration);
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}