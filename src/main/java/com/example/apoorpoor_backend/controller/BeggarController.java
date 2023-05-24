package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.BeggarRequestDto;
import com.example.apoorpoor_backend.dto.BeggarResponseDto;
import com.example.apoorpoor_backend.dto.StatusResponseDto;
import com.example.apoorpoor_backend.service.BeggarService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BeggarController {

    private final BeggarService beggarService;

    @PostMapping("/beggars")
    public StatusResponseDto createBeggar(@RequestBody BeggarRequestDto beggarRequestDto, Authentication authentication){
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return beggarService.createBeggar(beggarRequestDto, user.getUsername());
    }

    @GetMapping("/beggars")
    public BeggarResponseDto findBeggar(Authentication authentication){
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return beggarService.findBeggar(user.getUsername());
    }

    @PatchMapping("/beggars")
    public BeggarResponseDto updateBeggar(@RequestBody BeggarRequestDto beggarRequestDto, Authentication authentication){
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return beggarService.updateBeggar(beggarRequestDto, user.getUsername());
    }

}
