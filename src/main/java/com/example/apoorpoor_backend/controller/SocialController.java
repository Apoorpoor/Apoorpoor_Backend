package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.social.RankingResponseDto;
import com.example.apoorpoor_backend.dto.social.SocialResponseDto;
import com.example.apoorpoor_backend.dto.social.SocialSearchCondition;
import com.example.apoorpoor_backend.security.UserDetailsImpl;
import com.example.apoorpoor_backend.service.SocialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "SocialController", description = "소셜 랭킹 controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/social")
public class SocialController {

    private final SocialService socialService;

    @Operation(summary = "나이대별 소비/저축 평균 조회 API" , description = "나이대별 소비/저축 평균 조회")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "나이대별 소비/저축 평균 조회 완료" )})
    @GetMapping("/percent")
    public ResponseEntity<SocialResponseDto> getPercent(SocialSearchCondition condition, @AuthenticationPrincipal UserDetailsImpl userDetails){
     return socialService.getPercent(condition, userDetails.getUsername());
    }

    @Operation(summary = "랭킹 조회 API" , description = "랭킹 조회")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "랭킹 조회 완료" )})
    @GetMapping("/rank")
    public ResponseEntity<List<RankingResponseDto>> getRank(SocialSearchCondition condition){
        return socialService.getRank(condition);
    }
}
