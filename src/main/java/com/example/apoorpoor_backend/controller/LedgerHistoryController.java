package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistoryRequestDto;
import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistoryResponseDto;
import com.example.apoorpoor_backend.dto.common.StatusResponseDto;
import com.example.apoorpoor_backend.security.UserDetailsImpl;
import com.example.apoorpoor_backend.service.LedgerHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ledgerhistory")
public class LedgerHistoryController {
    private final LedgerHistoryService ledgerHistoryService;

    @PostMapping("/")
    public ResponseEntity<StatusResponseDto> createLedgerHistory(@RequestBody LedgerHistoryRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ledgerHistoryService.createLedgerHistory(requestDto, userDetails.getUsername());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LedgerHistoryResponseDto> updateLedgerHistory(@PathVariable Long id, @RequestBody LedgerHistoryRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ledgerHistoryService.updateLedgerHistory(id,requestDto, userDetails.getUsername());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LedgerHistoryResponseDto> getLedgerHistory(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ledgerHistoryService.getLedgerHistory(id, userDetails.getUsername());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<StatusResponseDto> deleteLedgerHistory(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ledgerHistoryService.deleteLedgerHistory(id, userDetails.getUsername());
    }
}
