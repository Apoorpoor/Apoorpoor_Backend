package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.auth.PrincipalDetails;
import com.example.apoorpoor_backend.dto.LedgerHistoryRequestDto;
import com.example.apoorpoor_backend.dto.LedgerHistoryResponseDto;
import com.example.apoorpoor_backend.dto.LedgerResponseDto;
import com.example.apoorpoor_backend.dto.StatusResponseDto;
import com.example.apoorpoor_backend.service.LedgerHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LedgerHistoryController {
    private final LedgerHistoryService ledgerHistoryService;

    @PostMapping("/ledgerhistory")
    public ResponseEntity<StatusResponseDto> createLedgerHistory(@RequestBody LedgerHistoryRequestDto requestDto, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return ledgerHistoryService.createLedgerHistory(requestDto, principalDetails.getUsername());
    }

    @PutMapping("/ledgerhistory/{id}")
    public ResponseEntity<LedgerHistoryResponseDto> updateLedgerHistory(@PathVariable Long id, @RequestBody LedgerHistoryRequestDto requestDto, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return ledgerHistoryService.updateLedgerHistory(id,requestDto, principalDetails.getUsername());
    }

    @GetMapping("/ledgerhistory/{id}")
    public ResponseEntity<LedgerHistoryResponseDto> getLedgerHistory(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return ledgerHistoryService.getLedgerHistory(id, principalDetails.getUsername());
    }
    @DeleteMapping("/ledgerhistory/{id}")
    public ResponseEntity<StatusResponseDto> deleteLedgerHistory(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return ledgerHistoryService.deleteLedgerHistory(id, principalDetails.getUsername());
    }
}
