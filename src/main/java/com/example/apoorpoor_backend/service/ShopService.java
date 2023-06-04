package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.beggar.BeggarExpUpResponseDto;
import com.example.apoorpoor_backend.dto.shop.ItemListResponseDto;
import com.example.apoorpoor_backend.dto.shop.ItemResponseDto;
import com.example.apoorpoor_backend.dto.shop.PayRequestDto;
import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.Item;
import com.example.apoorpoor_backend.model.enumType.ItemListEnum;
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

        if(itemType.equals("total")) {
            itemList = ItemListEnum.getEnumItemList();
        } else {
            itemList = ItemListEnum.getEnumItemListByType(itemType);
        }

        ItemListResponseDto itemListResponseDto = new ItemListResponseDto(itemList);
        return new ResponseEntity<>(itemListResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<BeggarExpUpResponseDto> buyPointUpdate(PayRequestDto payRequestDto, String username) {
        Beggar beggar = beggarCheck(username);
        String nickname = beggar.getNickname();
        Long exp = beggar.getExp();
        Long level = beggar.getLevel();
        Long itemPrice = payRequestDto.getItemListEnum().getItemPrice();
        Long updatePoint = beggar.getPoint() - itemPrice;

        BeggarExpUpResponseDto beggarExpUpResponseDto = BeggarExpUpResponseDto.builder()
                .nickname(nickname)
                .exp(exp)
                .level(level)
                .point(updatePoint)
                .build();

        beggar.updateExp(beggarExpUpResponseDto);

        Long itemNum = payRequestDto.getItemListEnum().getItemNum();
        String itemName = payRequestDto.getItemListEnum().getItemName();
        Long levelLimit = payRequestDto.getItemListEnum().getLevelLimit();
        String itemType = payRequestDto.getItemListEnum().getItemType();

        Item item = new Item(itemNum, itemName, levelLimit,  itemType, beggar);
        itemRepository.save(item);

        return new ResponseEntity<>(beggarExpUpResponseDto, HttpStatus.OK);
    }

    public Beggar beggarCheck(String username) {
        return beggarRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("푸어를 찾을 수 없습니다.")
        );
    }
}
