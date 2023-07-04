package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistoryRequestDto;
import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistoryResponseDto;
import com.example.apoorpoor_backend.dto.common.StatusResponseDto;
import com.example.apoorpoor_backend.security.UserDetailsImpl;
import com.example.apoorpoor_backend.service.LedgerHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "LedgerHistoryController", description = "거래내역 controller")
@RestController
@RequiredArgsConstructor
public class LedgerHistoryController {
    private final LedgerHistoryService ledgerHistoryService;

    @Operation(summary = "거래내역 등록 API" , description = "거래내역 등록")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "거래내역 등록 완료" )})
    @PostMapping("/ledgerhistory")
    public ResponseEntity<StatusResponseDto> createLedgerHistory(@RequestBody @Valid LedgerHistoryRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ledgerHistoryService.createLedgerHistory(requestDto, userDetails.getUsername());
    }

    @Operation(summary = "거래내역 수정 API" , description = "거래내역 수정")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "거래내역 수정 완료" )})
    @PutMapping("/ledgerhistory/{id}")
    public ResponseEntity<LedgerHistoryResponseDto> updateLedgerHistory(@PathVariable Long id, @RequestBody LedgerHistoryRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ledgerHistoryService.updateLedgerHistory(id,requestDto, userDetails.getUsername());
    }

    @Operation(summary = "거래내역 상세조회 API" , description = "거래내역 상세조회")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "거래내역 상세조회 완료" )})
    @GetMapping("/ledgerhistory/{id}")
    public ResponseEntity<LedgerHistoryResponseDto> getLedgerHistory(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ledgerHistoryService.getLedgerHistory(id, userDetails.getUsername());
    }

    @Operation(summary = "거래내역 삭제 API" , description = "거래내역 삭제")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "거래내역 삭제 완료" )})
    @DeleteMapping("/ledgerhistory/{id}")
    public ResponseEntity<StatusResponseDto> deleteLedgerHistory(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ledgerHistoryService.deleteLedgerHistory(id, userDetails.getUsername());
    }
}
