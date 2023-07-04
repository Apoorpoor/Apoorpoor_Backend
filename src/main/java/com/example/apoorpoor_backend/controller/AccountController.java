package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.account.*;
import com.example.apoorpoor_backend.dto.common.StatusResponseDto;
import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistoryResponseDto;
import com.example.apoorpoor_backend.security.UserDetailsImpl;
import com.example.apoorpoor_backend.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "AccountController", description = "가계부 controller")
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "가계부 생성 API" , description = "가계부 생성")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "가계부 생성 완료" )})
    @PostMapping("/account")
    public ResponseEntity<StatusResponseDto> createAccount(@Valid @RequestBody AccountRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return accountService.createAccount(requestDto, userDetails.getUsername());
    }

    @Operation(summary = "가계부 목록조회 API" , description = "가계부 목록조회")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "가계부 목록조회 완료" )})
    @GetMapping("/accounts")
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return accountService.getAllAccounts(userDetails.getUsername());
    }

    @Operation(summary = "가계부 상세조회 API" , description = "가계부 상세조회")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "가계부 상세조회 완료" )})
    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountResponseDto> getAccount(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return accountService.getAccount(id, userDetails.getUsername());
    }

    @Operation(summary = "가계부 수정 API" , description = "가계부 수정")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "가계부 수정 완료" )})
    @PatchMapping("/accounts/{id}")
    public ResponseEntity<AccountResponseDto> updateAccount(@PathVariable Long id, @RequestBody AccountRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return accountService.updateAccount(id, requestDto, userDetails.getUsername());
    }

    @Operation(summary = "가계부 삭제 API" , description = "가계부 삭제")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "가계부 삭제 완료" )})
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<StatusResponseDto> deleteAccount(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return accountService.deleteAccount(id, userDetails.getUsername());
    }

    @Operation(summary = "캘린더 일일 합계 조회 API" , description = "캘린더 일일 합계")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "캘린더 일일 합계 조회 완료" )})
    @GetMapping("/accounts/{id}/totalStatus")
    public ResponseEntity<AccountTotalListResponseDto> getTotalStatus(@PathVariable Long id, AccountSearchCondition condition, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return accountService.getTotalStatus(id, condition, userDetails.getUsername());
    }

    @Operation(summary = "상세 지출/소비 내역 조회 API" , description = "상세 지출/소비 내역")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "상세 지출/소비 내역 조회 완료" )})
    @GetMapping("/accounts/{id}/status")
    public Page<LedgerHistoryResponseDto> getStatus(@PathVariable Long id, AccountSearchCondition condition, Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return accountService.getStatus(id, condition, userDetails.getUsername(), pageable);
    }

    @Operation(summary = "이번달 상세 지출내역 조회 API" , description = "이번달 상세 지출내역")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "이번달 상세 지출내역 조회 완료" )})
    @GetMapping("/accounts/{id}/statistics")
    public Page<MonthSumResponseDto> getStatistics(@PathVariable Long id, AccountSearchCondition condition, Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return accountService.getStatistics(id, condition, userDetails.getUsername(), pageable);
    }

    @Operation(summary = "지난달/동월/동분기 대비 지출내역 조회 API" , description = "지난달/동월/동분기 대비 지출내역")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "지난달/동월/동분기 대비 지출내역 조회 완료" )})
    @GetMapping("/accounts/{id}/difference")
    public ResponseEntity<List<MonthSumResponseDto>> getDifference(@PathVariable Long id, AccountSearchCondition condition, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return accountService.getDifference(id, condition, userDetails.getUsername());
    }

}
