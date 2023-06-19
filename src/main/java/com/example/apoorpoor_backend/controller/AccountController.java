package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.account.*;
import com.example.apoorpoor_backend.dto.common.StatusResponseDto;
import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistoryResponseDto;
import com.example.apoorpoor_backend.security.UserDetailsImpl;
import com.example.apoorpoor_backend.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/account")
    public ResponseEntity<StatusResponseDto> createAccount(@Valid @RequestBody AccountRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
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

    @GetMapping("/accounts/{id}/totalStatus")
    public ResponseEntity<AccountTotalListResponseDto> getTotalStatus(@PathVariable Long id, AccountSearchCondition condition, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return accountService.getTotalStatus(id, condition, userDetails.getUsername());
    }

    @GetMapping("/accounts/{id}/status")
    public Page<LedgerHistoryResponseDto> getStatus(@PathVariable Long id, AccountSearchCondition condition, Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return accountService.getStatus(id, condition, userDetails.getUsername(), pageable);
    }

    @GetMapping("/accounts/{id}/statistics")
    public Page<MonthSumResponseDto> getStatistics(@PathVariable Long id, AccountSearchCondition condition, Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return accountService.getStatistics(id, condition, userDetails.getUsername(), pageable);
    }

    @GetMapping("/accounts/{id}/difference")
    public ResponseEntity<List<MonthSumResponseDto>> getDifference(@PathVariable Long id, AccountSearchCondition condition, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return accountService.getDifference(id, condition, userDetails.getUsername());
    }

}
