package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.auth.PrincipalDetails;
import com.example.apoorpoor_backend.auth.PrincipalDetailsService;
import com.example.apoorpoor_backend.dto.BeggarRequestDto;
import com.example.apoorpoor_backend.dto.BeggarResponseDto;
import com.example.apoorpoor_backend.dto.StatusResponseDto;
import com.example.apoorpoor_backend.service.BeggarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "BeggarController", description = "거지 캐릭터 controller")
@RestController
@RequiredArgsConstructor
public class BeggarController {

    private final BeggarService beggarService;

    @Operation(summary = "거지 캐릭터 생성 API" , description = "거지 캐릭터 생성")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "거지 캐릭터 생성 완료" )})
    @PostMapping("/beggars")
    public ResponseEntity<StatusResponseDto> createBeggar(@RequestBody BeggarRequestDto beggarRequestDto, @AuthenticationPrincipal PrincipalDetails principalDetails){
        //String username = authentication.getName();
        return beggarService.createBeggar(beggarRequestDto, principalDetails.getUsername());
    }

    @Operation(summary = "거지 캐릭터 검색 API" , description = "거지 캐릭터 검색")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "거지 캐릭터 검색 완료" )})
    @GetMapping("/beggars")
    public ResponseEntity<BeggarResponseDto> findBeggar(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return beggarService.findBeggar(principalDetails.getUsername());
    }

    @Operation(summary = "거지 캐릭터 업데이트 API" , description = "거지 캐릭터 update")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "거지 캐릭터 검색 완료" )})
    @PatchMapping("/beggars")
    public ResponseEntity<BeggarResponseDto> updateBeggar(@RequestBody BeggarRequestDto beggarRequestDto, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return beggarService.updateBeggar(beggarRequestDto, principalDetails.getUsername());
    }

}