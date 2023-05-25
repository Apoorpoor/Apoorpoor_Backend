package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.LedgerListResponseDto;
import com.example.apoorpoor_backend.dto.LedgerRequestDto;
import com.example.apoorpoor_backend.dto.LedgerResponseDto;
import com.example.apoorpoor_backend.dto.StatusResponseDto;
import com.example.apoorpoor_backend.service.FinancialLedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class FinancialLedgerController {

    private final FinancialLedgerService financialLedgerService;

    @PostMapping
    public ResponseEntity<LedgerResponseDto> createLedger(@RequestBody LedgerRequestDto ledgerRequestDto, Authentication authentication) {
        return financialLedgerService.createLedger(ledgerRequestDto, authentication);
    }

    @GetMapping
    public ResponseEntity<LedgerListResponseDto> getAllLedger(@RequestParam String searchLedgerTitle, Authentication authentication){
        return financialLedgerService.getAllLedger(searchLedgerTitle, authentication);
    }

    @GetMapping("/{accounts_id}")
    public ResponseEntity<LedgerResponseDto> getLedger(@PathVariable Long accounts_id, Authentication authentication){
        return financialLedgerService.getLedger(accounts_id, authentication);
    }

    @PatchMapping("/{accounts_id}")
    public ResponseEntity<LedgerResponseDto> updateLedger(@PathVariable Long accounts_id, @RequestBody LedgerRequestDto ledgerRequestDto, Authentication authentication){
        return financialLedgerService.updateLedger(accounts_id, ledgerRequestDto, authentication);
    }

    @DeleteMapping("/{accounts_id}")
    public ResponseEntity<StatusResponseDto> deleteLedger(@PathVariable Long accounts_id, Authentication authentication){
        return financialLedgerService.deleteLedger(accounts_id, authentication);
    }
}
