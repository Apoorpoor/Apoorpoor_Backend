package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.LedgerHistoryRequestDto;
import com.example.apoorpoor_backend.dto.LedgerHistoryResponseDto;
import com.example.apoorpoor_backend.dto.StatusResponseDto;
import com.example.apoorpoor_backend.security.UserDetailsImpl;
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
    public ResponseEntity<StatusResponseDto> createLedgerHistory(@RequestBody LedgerHistoryRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ledgerHistoryService.createLedgerHistory(requestDto, userDetails.getUsername());
    }

    @PutMapping("/ledgerhistory/{id}")
    public ResponseEntity<LedgerHistoryResponseDto> updateLedgerHistory(@PathVariable Long id, @RequestBody LedgerHistoryRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ledgerHistoryService.updateLedgerHistory(id,requestDto, userDetails.getUsername());
    }

    @GetMapping("/ledgerhistory/{id}")
    public ResponseEntity<LedgerHistoryResponseDto> getLedgerHistory(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ledgerHistoryService.getLedgerHistory(id, userDetails.getUsername());
    }
    @DeleteMapping("/ledgerhistory/{id}")
    public ResponseEntity<StatusResponseDto> deleteLedgerHistory(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ledgerHistoryService.deleteLedgerHistory(id, userDetails.getUsername());
    }
}
