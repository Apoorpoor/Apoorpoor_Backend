package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.*;
import com.example.apoorpoor_backend.security.UserDetailsImpl;
import com.example.apoorpoor_backend.service.BeggarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "BeggarController", description = "거지 캐릭터 controller")
@RestController
@RequiredArgsConstructor
public class BeggarController {

    private final BeggarService beggarService;

    @Operation(summary = "거지 캐릭터 생성 API" , description = "거지 캐릭터 생성")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "거지 캐릭터 생성 완료" )})
    @PostMapping("/beggar")
    public ResponseEntity<StatusResponseDto> createBeggar(@RequestBody BeggarRequestDto beggarRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return beggarService.createBeggar(beggarRequestDto, userDetails.getUsername());
    }

    @Operation(summary = "거지 캐릭터 검색 API" , description = "거지 캐릭터 검색")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "거지 캐릭터 검색 완료" )})
    @GetMapping("/beggar")
    public ResponseEntity<BeggarResponseDto> findBeggar(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return beggarService.findBeggar(userDetails.getUsername());
    }

    @Operation(summary = "거지 캐릭터 업데이트 API" , description = "거지 캐릭터 update")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "거지 캐릭터 검색 완료" )})
    @PatchMapping("/beggar")
    public ResponseEntity<BeggarResponseDto> updateBeggar(@RequestBody BeggarRequestDto beggarRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return beggarService.updateBeggar(beggarRequestDto, userDetails.getUsername());
    }

    @Operation(summary = "거지 캐릭터 경험치, 포인트 획득 API" , description = "거지 경험치, 포인트 획득")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "획득 완료" )})
    @PatchMapping("/beggar/point")
    public ResponseEntity<BeggarExpUpResponseDto> updateExp(@RequestBody BeggarExpUpRequestDto beggarExpUpRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return beggarService.updateExp(beggarExpUpRequestDto, userDetails.getUsername());
    }

//    @PatchMapping("/beggar/equip")
//    public ResponseEntity<>

}