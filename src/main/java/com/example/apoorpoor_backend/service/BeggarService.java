package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.*;
import com.example.apoorpoor_backend.dto.beggar.*;
import com.example.apoorpoor_backend.model.*;
import com.example.apoorpoor_backend.model.enumType.*;
import com.example.apoorpoor_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class BeggarService {

    private final BeggarRepository beggarRepository;
    private final UserRepository userRepository;
    private final GetBadgeRepository getBadgeRepository;
    private final BadgeRepository badgeRepository;
    private final ItemRepository itemRepository;

    public ResponseEntity<StatusResponseDto> createBeggar(BeggarRequestDto beggarRequestDto, String username) {
        User findUser = userCheck(username);

        Optional<Beggar> findBeggar = beggarRepository.findByUsername(username);
        if(findBeggar.isPresent())
            return new ResponseEntity<>(new StatusResponseDto("이미 푸어가 존재합니다."), HttpStatus.BAD_REQUEST);

        beggarRepository.save(new Beggar(beggarRequestDto, findUser));
        return new ResponseEntity<>(new StatusResponseDto("푸어가 생성되었어요..."), HttpStatus.OK );
    }

    public ResponseEntity<BeggarResponseDto> findBeggar(String username) {
        Beggar beggar = beggarCheck(username);
        return new ResponseEntity<>(BeggarResponseDto.of(beggar), HttpStatus.OK);
    }

    public ResponseEntity<BeggarResponseDto> updateBeggar(BeggarRequestDto beggarRequestDto, String username) {
        Beggar beggar = beggarCheck(username);
        beggar.update(beggarRequestDto);
        return new ResponseEntity<>(BeggarResponseDto.of(beggar), HttpStatus.OK);
    }

    public ResponseEntity<BeggarExpUpResponseDto> updateExp(BeggarExpUpRequestDto beggarExpUpRequestDto, String username) {
        Beggar beggar = beggarCheck(username);
        String nickname = beggar.getNickname();
        Long exp = beggar.getExp() + beggarExpUpRequestDto.getExpType().getAmount();
        Long point = beggar.getPoint() + beggarExpUpRequestDto.getExpType().getAmount();
        Long level = beggar.getLevel();

        if(beggarExpUpRequestDto.getExpType().equals(ExpType.GET_BADGE)) {
            saveBadge(beggarExpUpRequestDto.getBadgeType(), beggar);
        }

        if (LevelType.getNextExpByLevel(level) <= exp) {
            level ++;
        }

        BeggarExpUpResponseDto beggarExpUpResponseDto = BeggarExpUpResponseDto.builder()
                .nickname(nickname)
                .exp(exp)
                .level(level)
                .point(point)
                .build();

        beggar.updateExp(beggarExpUpResponseDto);
        return new ResponseEntity<>(beggarExpUpResponseDto, HttpStatus.OK);
    }

    public void updateExpNew(String username, Long plusPoint) {
        Beggar beggar = beggarCheck(username);
        beggar.updatePointAndExp(plusPoint);

        Long exp = beggar.getExp();
        Long level = beggar.getLevel();

        if (LevelType.getNextExpByLevel(level) <= exp) {
            level ++;
        }

//        BeggarExpUpResponseDto beggarExpUpResponseDto = BeggarExpUpResponseDto.builder()
//                .nickname(nickname)
//                .exp(exp)
//                .level(level)
//                .point(point)
//                .build();
//
//        beggar.updateExp(beggarExpUpResponseDto);
    }

    public void saveBadge(BadgeType badgeType, Beggar beggar) {
        Long badgeNum = badgeType.getBadgeNum();
        String badgeTitle = badgeType.getBadgeTitle();

        boolean hasBadge = beggar.getGetBadgeList().stream()
                .map(GetBadge::getBadge)
                .anyMatch(b -> b.getBadgeNum().equals(badgeNum));

        if(!hasBadge) {
            Badge badge = new Badge(badgeNum, badgeTitle);

            badgeRepository.save(badge);

            GetBadge getBadge = new GetBadge(badge, beggar);
            badge.getGetBadgeList().add(getBadge);
            getBadgeRepository.save(getBadge);

        } else {
            throw new IllegalArgumentException("이미 뱃지를 가지고 있습니다.");
        }
    }

    public ResponseEntity<String> customBeggar(BeggarCustomRequestDto beggarCustomRequestDto, String username) {
        Beggar beggar = beggarCheck(username);
        ItemListEnum itemListEnum = beggarCustomRequestDto.getItemListEnum();
        UnWearEnum unWearEnum = beggarCustomRequestDto.getUnWearEnum();

        if(unWearEnum != null) {
            String unWear = unWearEnum.getUnWearPart();

            switch (unWear) {
                case "tops" -> beggar.updateCustomTops(null);
                case "bottoms" -> beggar.updateCustomBottoms(null);
                case "shoes" -> beggar.updateCustomShoes(null);
                case "accessories" -> beggar.updateCustomAccessories(null);
                default -> throw new IllegalArgumentException("옳지 못한 행동입니다.");
            }
        } else {

            Item findItem = itemRepository.findItemByBeggar_IdAndItemNum(beggar.getId(),itemListEnum.getItemNum())
                    .orElseThrow(() -> new IllegalArgumentException("가지고 있지 않은 아이템 입니다.")
                    );

            String itemType = findItem.getItemType();

            switch (itemType) {
                case "tops" :
                    beggar.updateCustomTops(itemListEnum);
                    break;
                case "bottoms" :
                    beggar.updateCustomBottoms(itemListEnum);
                    break;
                case "shoes" :
                    beggar.updateCustomShoes(itemListEnum);
                case "accessories" :
                    beggar.updateCustomAccessories(itemListEnum);
                default:
                    throw new IllegalArgumentException("옳지 못한 행동입니다.");
            }
        }

        return new ResponseEntity<>("착용 완료", HttpStatus.OK);
    }

    public ResponseEntity<BeggarCustomListResponseDto> customList(String username) {
        Beggar beggar = beggarCheck(username);
        List<Item> hasItemList = itemRepository.findItemsByBeggar_Id(beggar.getId());
        List<ItemDto> itemsCollectionList = new ArrayList<>();

        for (Item item : hasItemList) {
            Long itemNum = item.getItemNum();
            String itemName = item.getItemName();
            Long levelLimit = item.getLevelLimit();
            String itemType = item.getItemType();

            ItemDto itemDto = new ItemDto(itemNum, itemName, levelLimit, itemType);

            itemsCollectionList.add(itemDto);

        }
        BeggarCustomListResponseDto beggarCustomListResponseDto = new BeggarCustomListResponseDto(itemsCollectionList);
        return new ResponseEntity<>(beggarCustomListResponseDto, HttpStatus.OK);
    }

    public User userCheck(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저 입니다.")
        );
    }

    public Beggar beggarCheck(String username) {
        return beggarRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("푸어를 찾을 수 없습니다.")
        );
    }
}
