package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.BeggarExpUpResponseDto;
import com.example.apoorpoor_backend.dto.shop.ItemListResponseDto;
import com.example.apoorpoor_backend.dto.shop.ItemResponseDto;
import com.example.apoorpoor_backend.dto.shop.PayRequestDto;
import com.example.apoorpoor_backend.dto.shop.PayResponseDto;
import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.enumType.Item;
import com.example.apoorpoor_backend.repository.BeggarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final BeggarRepository beggarRepository;

    public ResponseEntity<ItemListResponseDto> getItemList(String itemType) {
        List<ItemResponseDto> itemList;

        if(!itemType.equals("total")) {
            itemList = Item.getEnumItemListByType(itemType);
        } else {
            itemList = Item.getEnumItemList();
        }

        ItemListResponseDto itemListResponseDto = new ItemListResponseDto(itemList);
        return new ResponseEntity<>(itemListResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<PayResponseDto> buyItem(PayRequestDto payRequestDto, String username) {
        Beggar beggar = beggarCheck(username);
        Long itemPrice = payRequestDto.getItem().getItemPrice();
        Long updatePoint = beggar.getPoint() - itemPrice;

        BeggarExpUpResponseDto beggarExpUpResponseDto = BeggarExpUpResponseDto.builder()
                .point(updatePoint)
                .build();

        beggar.updateExp(beggarExpUpResponseDto);

        ItemResponseDto itemResponseDto = ItemResponseDto.builder()
                .itemNum(payRequestDto.getItem().getItemNum())
                .itemName(payRequestDto.getItem().getItemName())
                .itemPrice(payRequestDto.getItem().getItemPrice())
                .levelLimit(payRequestDto.getItem().getLevelLimit())
                .itemType(payRequestDto.getItem().getItemType())
                .build();

        PayResponseDto payResponseDto = PayResponseDto.builder()
                .itemResponseDto(itemResponseDto)
                .point(updatePoint)
                .build();

        return new ResponseEntity<>(payResponseDto, HttpStatus.OK);
    }

    public Beggar beggarCheck(String username) {
        return beggarRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("푸어를 찾을 수 없습니다.")
        );
    }
}
