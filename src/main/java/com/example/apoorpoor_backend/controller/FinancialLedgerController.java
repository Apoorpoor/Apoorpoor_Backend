package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.auth.PrincipalDetails;
import com.example.apoorpoor_backend.dto.LedgerListResponseDto;
import com.example.apoorpoor_backend.dto.LedgerRequestDto;
import com.example.apoorpoor_backend.dto.LedgerResponseDto;
import com.example.apoorpoor_backend.dto.StatusResponseDto;
import com.example.apoorpoor_backend.service.FinancialLedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class FinancialLedgerController {
    /*
    SpringSecurity session -> Authentication -> UserDetails, Oauth2UserDetails
     */
    private final FinancialLedgerService financialLedgerService;

    @PostMapping
    public ResponseEntity<LedgerResponseDto> createLedger(@RequestBody LedgerRequestDto ledgerRequestDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return financialLedgerService.createLedger(ledgerRequestDto, principalDetails);
    }

    @GetMapping
    public ResponseEntity<LedgerListResponseDto> getAllLedger(@RequestParam String searchLedgerTitle, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return financialLedgerService.getAllLedger(searchLedgerTitle, principalDetails);
    }

    @GetMapping("/{ledgerTitle}")
    public ResponseEntity<LedgerResponseDto> getLedger(@PathVariable String ledgerTitle, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return financialLedgerService.getLedger(ledgerTitle, principalDetails);
    }

    @PatchMapping("/{ledgerTitle}")
    public ResponseEntity<LedgerResponseDto> updateLedger(@PathVariable String ledgerTitle, @RequestBody LedgerRequestDto ledgerRequestDto, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return financialLedgerService.updateLedger(ledgerTitle, ledgerRequestDto, principalDetails);
    }

    @DeleteMapping("/{ledgerTitle}")
    public ResponseEntity<StatusResponseDto> deleteLedger(@PathVariable String ledgerTitle, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return financialLedgerService.deleteLedger(ledgerTitle, principalDetails);
    }
}