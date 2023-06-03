package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.shop.ItemListResponseDto;
import com.example.apoorpoor_backend.dto.shop.ItemResponseDto;
import com.example.apoorpoor_backend.model.enumType.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {
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

}
