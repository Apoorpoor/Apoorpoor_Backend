package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.social.RankingResponseDto;
import com.example.apoorpoor_backend.dto.social.SocialResponseDto;
import com.example.apoorpoor_backend.dto.social.SocialSearchCondition;
import com.example.apoorpoor_backend.security.UserDetailsImpl;
import com.example.apoorpoor_backend.service.SocialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SocialController {

    private final SocialService socialService;

    @GetMapping("/social/percent")
    public ResponseEntity<SocialResponseDto> getPercent(SocialSearchCondition condition, @AuthenticationPrincipal UserDetailsImpl userDetails){
     return socialService.getPercent(condition, userDetails.getUsername());
    }

    @GetMapping("/social/rank")
    public ResponseEntity<List<RankingResponseDto>> getRank(SocialSearchCondition condition){
        return socialService.getRank(condition);
    }
}
