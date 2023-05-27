package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.auth.PrincipalDetails;
import com.example.apoorpoor_backend.dto.BeggarRequestDto;
import com.example.apoorpoor_backend.dto.BeggarResponseDto;
import com.example.apoorpoor_backend.dto.StatusResponseDto;
import com.example.apoorpoor_backend.service.BeggarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BeggarController {

    private final BeggarService beggarService;

    @PostMapping("/beggars")
    public ResponseEntity<StatusResponseDto> createBeggar(@RequestBody BeggarRequestDto beggarRequestDto, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return beggarService.createBeggar(beggarRequestDto, principalDetails.getUsername());
    }

    @GetMapping("/beggars")
    public ResponseEntity<BeggarResponseDto> findBeggar(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return beggarService.findBeggar(principalDetails.getUsername());
    }

    @PatchMapping("/beggars")
    public ResponseEntity<BeggarResponseDto> updateBeggar(@RequestBody BeggarRequestDto beggarRequestDto, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return beggarService.updateBeggar(beggarRequestDto, principalDetails.getUsername());
    }

}