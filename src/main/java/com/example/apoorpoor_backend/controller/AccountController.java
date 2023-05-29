package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.auth.PrincipalDetails;
import com.example.apoorpoor_backend.dto.AccountRequestDto;
import com.example.apoorpoor_backend.dto.AccountResponseDto;
import com.example.apoorpoor_backend.dto.StatusResponseDto;
import com.example.apoorpoor_backend.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/account")
    public ResponseEntity<StatusResponseDto> createAccount(@RequestBody AccountRequestDto requestDto, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return accountService.createAccount(requestDto, principalDetails.getUsername());
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return accountService.getAllAccounts(principalDetails.getUsername());
    }
    
    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountResponseDto> getAccount(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return accountService.getAccount(id, principalDetails.getUsername());
    }

    @PatchMapping("/accounts/{id}")
    public ResponseEntity<AccountResponseDto> updateAccount(@PathVariable Long id, @RequestBody AccountRequestDto requestDto, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return accountService.updateAccount(id, requestDto, principalDetails.getUsername());
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<StatusResponseDto> deleteAccount(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return accountService.deleteAccount(id, principalDetails.getUsername());
    }

}
