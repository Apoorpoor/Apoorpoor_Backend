package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.service.KakaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "OAuthController", description = "OAuth controller")
@RestController
@AllArgsConstructor
public class OAuthController {

    private final KakaoService kakaoService;

    @Operation(summary = "카카오 로그인 API" , description = "카카오 로그인")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "카카오 로그인 완료" )})
    @GetMapping("/oauth/kakao")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        return kakaoService.kakaoLogin(code, response);
    }
}
