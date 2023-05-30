package com.example.apoorpoor_backend.jwt;

import com.example.apoorpoor_backend.auth.PrincipalDetailsService;
import com.example.apoorpoor_backend.dto.TokenDto;
import com.example.apoorpoor_backend.model.RefreshToken;
import com.example.apoorpoor_backend.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secretKey}")
    private String secretKey; // 암호화/복호화에 필요

    public static final String ACCESS_KEY = "Authorization";
    public static final String REFRESH_KEY = "Authorization_Refresh";

    public static final String BEARER_PREFIX = "Bearer ";

    private static final long ACCESS_TIME = 2 * 60 * 60 * 1000L;//60 * 60 * 1000L;
    private static final long REFRESH_TIME = 24 * 60 * 60 * 1000L;


    private final PrincipalDetailsService principalDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public TokenDto createAllToken(String username) {
        return new TokenDto(createToken(username, "Access"), createToken(username, "Refresh"));
    }

    public String resolveToken(jakarta.servlet.http.HttpServletRequest request, String token) {
        String tokenName = token.equals("Authorization") ? ACCESS_KEY : REFRESH_KEY;
        String bearerToken = request.getHeader(tokenName);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String createToken(String username, String type) {
        Date date = new Date();
        long time = type.equals("Access") ? ACCESS_TIME : REFRESH_TIME;

        return BEARER_PREFIX
                + Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                //.claim(AUTHORIZATION_KEY, role)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + time))
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않은 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public String getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication createAuthentication(String username) {
        UserDetails userDetails = principalDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public Boolean refreshTokenValidation(String token) {
        if (!validateToken(token)) return false;

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUsername(getUserInfoFromToken(token));

        return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken().split(" ")[1].trim());
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader(ACCESS_KEY, accessToken);
    }

    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader(REFRESH_KEY, refreshToken);
    }
}