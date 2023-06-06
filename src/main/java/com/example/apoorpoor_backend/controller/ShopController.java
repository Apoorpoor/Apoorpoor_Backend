package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.beggar.BeggarExpUpResponseDto;
import com.example.apoorpoor_backend.dto.shop.ItemListResponseDto;
import com.example.apoorpoor_backend.dto.shop.PayRequestDto;
import com.example.apoorpoor_backend.security.UserDetailsImpl;
import com.example.apoorpoor_backend.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ShopController", description = "상점 controller")
@RestController
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @Operation(summary = "상점 물품 목록 조회 API" , description = "상점 Beggar 레벨에 따라 물품 조회")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "상품 리스트 조회 완료" )})
    @GetMapping("/shop")
    public ResponseEntity<ItemListResponseDto> getItemList(
            @RequestParam("itemType") String itemType, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return shopService.getItemList(itemType, userDetails.getUsername());
    }

    @Operation(summary = "상점 물품 구매 API" , description = "상점 물품 구매")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "상품 구매 완료" )})
    @PatchMapping("/pay")
    public ResponseEntity<BeggarExpUpResponseDto> buyPointUpdate(
            @RequestBody PayRequestDto payRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return shopService.buyPointUpdate(payRequestDto, userDetails.getUsername());
    }

    @GetMapping("/point")
    public Page<PointResponseDto> getPointList(PointSearchCondition condition, Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return shopService.getPointList(condition, pageable, userDetails.getUsername());
    }



}
