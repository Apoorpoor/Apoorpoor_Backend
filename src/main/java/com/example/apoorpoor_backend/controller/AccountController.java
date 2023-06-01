package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.AccountRequestDto;
import com.example.apoorpoor_backend.dto.AccountResponseDto;
import com.example.apoorpoor_backend.dto.StatusResponseDto;
import com.example.apoorpoor_backend.security.UserDetailsImpl;
import com.example.apoorpoor_backend.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/account")
    public ResponseEntity<StatusResponseDto> createAccount(@RequestBody AccountRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return accountService.createAccount(requestDto, userDetails.getUsername());
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return accountService.getAllAccounts(userDetails.getUsername());
    }
    
    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountResponseDto> getAccount(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return accountService.getAccount(id, userDetails.getUsername());
    }

    @PatchMapping("/accounts/{id}")
    public ResponseEntity<AccountResponseDto> updateAccount(@PathVariable Long id, @RequestBody AccountRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return accountService.updateAccount(id, requestDto, userDetails.getUsername());
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<StatusResponseDto> deleteAccount(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return accountService.deleteAccount(id, userDetails.getUsername());
    }

}
