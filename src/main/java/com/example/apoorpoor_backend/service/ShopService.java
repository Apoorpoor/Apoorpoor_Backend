package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.beggar.BeggarExpUpResponseDto;
import com.example.apoorpoor_backend.dto.shop.ItemListResponseDto;
import com.example.apoorpoor_backend.dto.shop.ItemResponseDto;
import com.example.apoorpoor_backend.dto.shop.PayRequestDto;
import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.Item;
import com.example.apoorpoor_backend.model.Point;
import com.example.apoorpoor_backend.model.enumType.ItemListEnum;
import com.example.apoorpoor_backend.repository.PointRepository;
import com.example.apoorpoor_backend.repository.beggar.BeggarRepository;
import com.example.apoorpoor_backend.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopService {

    private final BeggarRepository beggarRepository;

    private final ItemRepository itemRepository;

    private final PointRepository pointRepository;

    public ResponseEntity<ItemListResponseDto> getItemList(String itemType, String username) {
        Beggar beggar = beggarCheck(username);
        List<Item> hasItemList = itemRepository.findItemsByBeggar_Id(beggar.getId());
        List<Long> hasItemNumDtoList = new ArrayList<>();

        for (Item item : hasItemList) {
            Long itemNum = item.getItemNum();

            hasItemNumDtoList.add(itemNum);
        }

        List<ItemResponseDto> itemList;
        if (itemType.equals("total")) {
            itemList = ItemListEnum.getEnumItemList(beggar, hasItemNumDtoList);
        } else {
            itemList = ItemListEnum.getEnumItemListByType(itemType, beggar, hasItemNumDtoList);
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

        if (updatePoint < 0) {
            throw new IllegalArgumentException("포인트가 부족하여 구매할 수 없습니다.");
        }

        String pointDescription = payRequestDto.getItemListEnum().getItemName() + "구매!!";//////////////////////////////////////

        Long itemNum = payRequestDto.getItemListEnum().getItemNum();
        String itemName = payRequestDto.getItemListEnum().getItemName();
        Long levelLimit = payRequestDto.getItemListEnum().getLevelLimit();
        String itemType = payRequestDto.getItemListEnum().getItemType();

        if (itemRepository.existsDistinctByBeggar_IdAndItemNum(beggar.getId(), itemNum)) {
            throw new IllegalArgumentException("이미 존재하는 아이템 입니다.");
        }

        Item item = new Item(itemNum, itemName, levelLimit, itemType, beggar);
        itemRepository.save(item);


        BeggarExpUpResponseDto beggarExpUpResponseDto = BeggarExpUpResponseDto.builder()
                .nickname(nickname)
                .exp(exp)
                .level(level)
                .point(updatePoint)
                .build();

        //////////////////////////////////////////////////////////////////////////////
        Point recordPoint = new Point(pointDescription, null, itemPrice, beggar);
        pointRepository.save(recordPoint);
      //////////////////////////////////////////////////////////////////////////////

        beggar.updateExp(beggarExpUpResponseDto);


        return new ResponseEntity<>(beggarExpUpResponseDto, HttpStatus.OK);
    }

    public Beggar beggarCheck(String username) {
        return beggarRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("푸어를 찾을 수 없습니다.")
        );
    }
}
