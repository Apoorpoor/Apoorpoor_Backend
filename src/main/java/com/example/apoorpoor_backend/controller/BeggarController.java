package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.*;
import com.example.apoorpoor_backend.dto.beggar.*;
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
//    public ResponseEntity<StatusResponseDto> createBeggar(@RequestBody BeggarRequestDto beggarRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return beggarService.createBeggar(beggarRequestDto, userDetails.getUsername());
//    }

    public ResponseEntity<StatusResponseDto> createBeggar(@RequestBody BeggarRequestDto beggarRequestDto){
        String username = "user";
        return beggarService.createBeggar(beggarRequestDto, username);
    }

    @Operation(summary = "마이 거지 캐릭터 API" , description = "내 거지 캐릭터 검색")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "거지 캐릭터 검색 완료" )})
    @GetMapping("/beggar")
//    public ResponseEntity<BeggarSearchResponseDto> myBeggar(@AuthenticationPrincipal UserDetailsImpl userDetails){
//        return beggarService.myBeggar(userDetails.getUsername());
//    }

    public ResponseEntity<BeggarSearchResponseDto> myBeggar(){
        String username = "user";
        return beggarService.myBeggar(username);
    }

    @Operation(summary = "거지 캐릭터 업데이트 API" , description = "거지 캐릭터 update")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "거지 캐릭터 검색 완료" )})
    @PatchMapping("/beggar")
//    public ResponseEntity<BeggarResponseDto> updateBeggar(@RequestBody BeggarRequestDto beggarRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return beggarService.updateBeggar(beggarRequestDto, userDetails.getUsername());
//    }

    public ResponseEntity<BeggarResponseDto> updateBeggar(@RequestBody BeggarRequestDto beggarRequestDto){
        String username = "user";
        return beggarService.updateBeggar(beggarRequestDto, username);
    }

    @Operation(summary = "거지 캐릭터 경험치, 포인트 획득 API" , description = "거지 경험치, 포인트 획득")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "획득 완료" )})
    @PatchMapping("/beggar/point")
//    public ResponseEntity<BeggarExpUpResponseDto> updateExp(@RequestBody BeggarExpUpRequestDto beggarExpUpRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return beggarService.updateExp(beggarExpUpRequestDto, userDetails.getUsername());
//    }

    public ResponseEntity<BeggarExpUpResponseDto> updateExp(@RequestBody BeggarExpUpRequestDto beggarExpUpRequestDto) {
        String username = "user";
        return beggarService.updateExp(beggarExpUpRequestDto, username);
    }

    @Operation(summary = "거지 캐릭터 커스텀 API" , description = "거지캐릭터에게 구매한 옷 입히기")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "옷 입히기 완료" )})
    @PatchMapping("/beggar/custom")
//    public ResponseEntity<String> customBeggar(@RequestBody BeggarCustomRequestDto beggarCustomRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return beggarService.customBeggar(beggarCustomRequestDto, userDetails.getUsername());
//    }
    public ResponseEntity<String> customBeggar(@RequestBody BeggarCustomRequestDto beggarCustomRequestDto) {
        String username = "user";
        return beggarService.customBeggar(beggarCustomRequestDto, username);
    }

    @Operation(summary = "거지 캐릭터 커스텀 조회 API" , description = "거지캐릭터가 구매한 아이템 조회")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "조회 완료" )})
    @GetMapping("/beggar/custom")
//    public ResponseEntity<BeggarCustomListResponseDto> customList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return beggarService.customList(userDetails.getUsername());
//    }

    public ResponseEntity<BeggarCustomListResponseDto> customList() {
        String username = "user";
        return beggarService.customList(username);
    }

}