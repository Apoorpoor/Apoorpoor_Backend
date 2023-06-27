package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.beggar.BeggarExpUpResponseDto;
import com.example.apoorpoor_backend.dto.shop.*;
import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.Item;
import com.example.apoorpoor_backend.model.Point;
import com.example.apoorpoor_backend.model.enumType.ItemListEnum;
import com.example.apoorpoor_backend.repository.shop.PointRepository;
import com.example.apoorpoor_backend.repository.beggar.BeggarRepository;
import com.example.apoorpoor_backend.repository.shop.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopService {

    @Value("${secret.url.item}")
    private String itemUrl;

    private final BeggarRepository beggarRepository;

    private final ItemRepository itemRepository;

    private final PointRepository pointRepository;

    @Transactional(readOnly = true)
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
            itemList = getEnumItemList(beggar, hasItemNumDtoList);
        } else {
            itemList = getEnumItemListByType(itemType, beggar, hasItemNumDtoList);
        }

        ItemListResponseDto itemListResponseDto = ItemListResponseDto.builder().
                itemList(itemList)
                .build();
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

        Long itemNum = payRequestDto.getItemListEnum().getItemNum();
        String itemName = payRequestDto.getItemListEnum().getItemName();
        Long levelLimit = payRequestDto.getItemListEnum().getLevelLimit();
        String itemType = payRequestDto.getItemListEnum().getItemType();

        if (itemRepository.existsDistinctByBeggar_IdAndItemNum(beggar.getId(), itemNum)) {
            throw new IllegalArgumentException("이미 존재하는 아이템 입니다.");
        }

        String pointDescription = payRequestDto.getItemListEnum().getItemName() + "구매!!";

        Item item = Item.builder()
                .itemNum(itemNum)
                .itemName(itemName)
                .levelLimit(levelLimit)
                .itemType(itemType)
                .beggar(beggar)
                .build();

        itemRepository.save(item);


        BeggarExpUpResponseDto beggarExpUpResponseDto = BeggarExpUpResponseDto.builder()
                .nickname(nickname)
                .exp(exp)
                .level(level)
                .point(updatePoint)
                .build();

        Point recordPoint = Point.builder()
                .pointDescription(pointDescription)
                .earnedPoint(null)
                .usedPoints(itemPrice)
                .beggar(beggar)
                .build();

        pointRepository.save(recordPoint);


        beggar.updateExp(beggarExpUpResponseDto);


        return new ResponseEntity<>(beggarExpUpResponseDto, HttpStatus.OK);
    }

    public Page<PointResponseDto> getPointList(PointSearchCondition condition, Pageable pageable, String username ) {
        Beggar beggar = beggarCheck(username);
        return pointRepository.findAllByPeriodAndBeggar(beggar.getId(), condition, pageable);
    }

    public List<ItemResponseDto> getEnumItemList(Beggar beggar, List<Long> hasItemNumDtoList) {
        List<ItemResponseDto> itemList = new ArrayList<>();

        int itemListEnumCount = (int) Arrays.stream(ItemListEnum.values()).count();
        Long topsNum = beggar.getTop() == null ? null : beggar.getTop().getItemNum();
        Long bottomsNum = beggar.getBottom() == null ? null : beggar.getBottom().getItemNum();
        Long shoesNum = beggar.getShoes() == null ? null : beggar.getShoes().getItemNum();
        Long accessoriesNum = beggar.getAcc() == null ? null : beggar.getAcc().getItemNum();
        Long customsNum = beggar.getCustom() == null ? null : beggar.getCustom().getItemNum();


        String[] matches = new String[itemListEnumCount];

        for (Long aLong : hasItemNumDtoList) {
            matches[Math.toIntExact(aLong)] = "DONE";
        }

        int i = 0;
        for (ItemListEnum itemListEnum : ItemListEnum.values()) {
            Long itemNum = itemListEnum.getItemNum();
            String itemName = itemListEnum.getItemName();
            Long itemPrice = itemListEnum.getItemPrice();
            Long levelLimit = itemListEnum.getLevelLimit();
            String itemType = itemListEnum.getItemType();
            String itemState = matches[i];
            String itemImage = itemUrl + itemListEnum.getItemImage();

            if (Objects.equals(topsNum, itemNum)) itemState = "EQUIPPED";
            if (Objects.equals(bottomsNum, itemNum)) itemState = "EQUIPPED";
            if (Objects.equals(shoesNum, itemNum)) itemState = "EQUIPPED";
            if (Objects.equals(accessoriesNum, itemNum)) itemState = "EQUIPPED";
            if (Objects.equals(customsNum, itemNum)) itemState = "EQUIPPED";

            ItemResponseDto dto = ItemResponseDto.builder()
                    .itemNum(itemNum)
                    .itemName(itemName)
                    .itemPrice(itemPrice)
                    .levelLimit(levelLimit)
                    .itemType(itemType)
                    .itemState(itemState)
                    .itemImage(itemImage)
                    .build();
            itemList.add(dto);

            i++;
        }
        return itemList;
    }

    public List<ItemResponseDto> getEnumItemListByType(String itemType, Beggar beggar, List<Long> hasItemNumDtoList) {
        List<ItemResponseDto> filteredItemList = new ArrayList<>();

        int itemListEnumCount = (int) Arrays.stream(ItemListEnum.values()).count();
        Long topsNum = beggar.getTop() == null ? null : beggar.getTop().getItemNum();
        Long bottomsNum = beggar.getBottom() == null ? null : beggar.getBottom().getItemNum();
        Long shoesNum = beggar.getShoes() == null ? null : beggar.getShoes().getItemNum();
        Long accessoriesNum = beggar.getAcc() == null ? null : beggar.getAcc().getItemNum();
        Long customsNum = beggar.getCustom() == null? null : beggar.getCustom().getItemNum();

        String[] matches = new String[itemListEnumCount];

        for (Long aLong : hasItemNumDtoList) {
            matches[Math.toIntExact(aLong)] = "DONE";
        }

        int i = 0;

        for (ItemListEnum itemListEnum : ItemListEnum.values()) {
            if (itemListEnum.getItemType().equals(itemType)) {

                Long itemNum = itemListEnum.getItemNum();
                String itemName = itemListEnum.getItemName();
                Long itemPrice = itemListEnum.getItemPrice();
                Long levelLimit = itemListEnum.getLevelLimit();
                String itemState = matches[i];
                String itemImage = itemUrl + itemListEnum.getItemImage();


                if (Objects.equals(topsNum, itemNum)) itemState = "EQUIPPED";
                if (Objects.equals(bottomsNum, itemNum)) itemState = "EQUIPPED";
                if (Objects.equals(shoesNum, itemNum)) itemState = "EQUIPPED";
                if (Objects.equals(accessoriesNum, itemNum)) itemState = "EQUIPPED";
                if (Objects.equals(customsNum, itemNum)) itemState = "EQUIPPED";


                ItemResponseDto dto = ItemResponseDto.builder()
                        .itemNum(itemNum)
                        .itemName(itemName)
                        .itemPrice(itemPrice)
                        .levelLimit(levelLimit)
                        .itemType(itemType)
                        .itemState(itemState)
                        .itemImage(itemImage)
                        .build();
                filteredItemList.add(dto);

            }
            i++;
        }
        return filteredItemList;
    }

    public Beggar beggarCheck(String username) {
        return beggarRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("푸어를 찾을 수 없습니다.")
        );
    }

}
