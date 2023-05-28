package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.auth.PrincipalDetails;
import com.example.apoorpoor_backend.dto.LedgerListResponseDto;
import com.example.apoorpoor_backend.dto.LedgerRequestDto;
import com.example.apoorpoor_backend.dto.LedgerResponseDto;
import com.example.apoorpoor_backend.dto.StatusResponseDto;
import com.example.apoorpoor_backend.service.FinancialLedgerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "FinancialLedgerController", description = "가계부 controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class FinancialLedgerController {

    private final FinancialLedgerService financialLedgerService;

    @Operation(summary = "가계부 생성 API" , description = "가계부 생성")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "가계부 생성 완료" )})
    @PostMapping
    public ResponseEntity<LedgerResponseDto> createLedger(@RequestBody LedgerRequestDto ledgerRequestDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return financialLedgerService.createLedger(ledgerRequestDto, principalDetails);
    }

    @Operation(summary = "특정 가계부 목록 전체조회  API" , description = "가계부이름으로 해당 가계부 목록 전체 조회")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "가계부 조회 완료" )})
    @GetMapping
    public ResponseEntity<LedgerListResponseDto> getAllLedger(@RequestParam String ledgerTitle, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return financialLedgerService.getAllLedger(ledgerTitle, principalDetails);
    }

    @Operation(summary = "가계부 상세 조회  API" , description = "가계부 목록 상세 조회")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "가계부 조회 완료" )})
    @GetMapping("/{ledgerTitle}")
    public ResponseEntity<LedgerResponseDto> getLedger(@PathVariable String ledgerTitle, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return financialLedgerService.getLedger(ledgerTitle, principalDetails);
    }

    @Operation(summary = "가계부 업데이트  API" , description = "가계부 업데이트")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "가계부 업데이트 완료" )})
    @PatchMapping("/{ledgerTitle}")
    public ResponseEntity<LedgerResponseDto> updateLedger(@PathVariable String ledgerTitle, @RequestBody LedgerRequestDto ledgerRequestDto, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return financialLedgerService.updateLedger(ledgerTitle, ledgerRequestDto, principalDetails);
    }

    @Operation(summary = "가계부 삭제  API" , description = "가계부 삭제")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "가계부 삭제 완료" )})
    @DeleteMapping("/{ledgerTitle}")
    public ResponseEntity<StatusResponseDto> deleteLedger(@PathVariable String ledgerTitle, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return financialLedgerService.deleteLedger(ledgerTitle, principalDetails);
    }
}