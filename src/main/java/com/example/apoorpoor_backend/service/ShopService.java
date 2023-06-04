package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.BeggarExpUpResponseDto;
import com.example.apoorpoor_backend.dto.shop.ItemListResponseDto;
import com.example.apoorpoor_backend.dto.shop.ItemResponseDto;
import com.example.apoorpoor_backend.dto.shop.PayRequestDto;
import com.example.apoorpoor_backend.dto.shop.PayResponseDto;
import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.Item;
import com.example.apoorpoor_backend.model.enumType.ItemType;
import com.example.apoorpoor_backend.repository.BeggarRepository;
import com.example.apoorpoor_backend.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopService {

    private final BeggarRepository beggarRepository;

    private final ItemRepository itemRepository;

    public ResponseEntity<ItemListResponseDto> getItemList(String itemType) {
        List<ItemResponseDto> itemList;

        if(!itemType.equals("total")) {
            itemList = ItemType.getEnumItemListByType(itemType);
        } else {
            itemList = ItemType.getEnumItemList();
        }

        ItemListResponseDto itemListResponseDto = new ItemListResponseDto(itemList);
        return new ResponseEntity<>(itemListResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<PayResponseDto> buyItem(PayRequestDto payRequestDto, String username) {
        Beggar beggar = beggarCheck(username);
        Long itemPrice = payRequestDto.getItemType().getItemPrice();
        Long updatePoint = beggar.getPoint() - itemPrice;
        ItemType itemType = payRequestDto.getItemType();

        BeggarExpUpResponseDto beggarExpUpResponseDto = BeggarExpUpResponseDto.builder()
                .point(updatePoint)
                .build();

        beggar.updateExp(beggarExpUpResponseDto);

        ItemResponseDto itemResponseDto = ItemResponseDto.builder()
                .itemNum(payRequestDto.getItemType().getItemNum())
                .itemName(payRequestDto.getItemType().getItemName())
                .itemPrice(payRequestDto.getItemType().getItemPrice())
                .levelLimit(payRequestDto.getItemType().getLevelLimit())
                .itemType(payRequestDto.getItemType().getItemType())
                .build();

        Item item = new Item(itemType, beggar);
        itemRepository.save(item);

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
