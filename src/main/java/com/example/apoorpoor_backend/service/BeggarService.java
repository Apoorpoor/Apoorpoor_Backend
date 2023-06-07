package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.beggar.*;
import com.example.apoorpoor_backend.dto.common.StatusResponseDto;
import com.example.apoorpoor_backend.model.*;
import com.example.apoorpoor_backend.model.enumType.*;
import com.example.apoorpoor_backend.repository.*;
import com.example.apoorpoor_backend.repository.beggar.BeggarRepository;
import com.example.apoorpoor_backend.repository.ledgerhistory.LedgerHistoryRepository;
import com.example.apoorpoor_backend.repository.shop.ItemRepository;
import com.example.apoorpoor_backend.repository.shop.PointRepository;
import com.example.apoorpoor_backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BeggarService {
    @Value("${secret.url.item}")
    private String itemUrl;
    private final BeggarRepository beggarRepository;
    private final UserRepository userRepository;
    private final GetBadgeRepository getBadgeRepository;
    private final BadgeRepository badgeRepository;
    private final ItemRepository itemRepository;
    private final LedgerHistoryRepository ledgerHistoryRepository;
    private final PointRepository pointRepository;

    public ResponseEntity<StatusResponseDto> createBeggar(BeggarRequestDto beggarRequestDto, String username) {
        User findUser = userCheck(username);

        Optional<Beggar> findBeggar = beggarRepository.findByUsername(username);
        if(findBeggar.isPresent())
            return new ResponseEntity<>(new StatusResponseDto("이미 푸어가 존재합니다."), HttpStatus.BAD_REQUEST);

        beggarRepository.save(new Beggar(beggarRequestDto, findUser));
        return new ResponseEntity<>(new StatusResponseDto("푸어가 생성되었어요..."), HttpStatus.OK );
    }

    public ResponseEntity<BeggarSearchResponseDto> myBeggar(String username) {
        User user = userCheck(username);
        Beggar beggar = beggarCheck(username);

        Long beggarId = beggar.getId();
        Long userId = user.getId();
        String nickname = beggar.getNickname();
        Long point = beggar.getPoint();
        Long level = beggar.getLevel();
        String description = beggar.getDescription();
        String gender = user.getGender();
        Long age = user.getAge();
        String topImage = beggar.getTop() == null ? null : itemUrl + beggar.getTop().getItemImage();
        String bottomImage = beggar.getBottom() == null ? null : itemUrl  + beggar.getBottom().getItemImage();
        String shoesImage = beggar.getShoes() == null ? null : itemUrl  + beggar.getShoes().getItemImage();
        String accImage = beggar.getAcc() == null ? null : itemUrl  + beggar.getAcc().getItemImage();

        BeggarSearchResponseDto beggarSearchResponseDto = BeggarSearchResponseDto
                .builder().beggarId(beggarId)
                .userId(userId).nickname(nickname)
                .point(point).level(level)
                .description(description).gender(gender)
                .age(age).topImage(topImage).bottomImage(bottomImage)
                .shoesImage(shoesImage).accImage(accImage)
                .build();

        return new ResponseEntity<>(beggarSearchResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<BeggarResponseDto> updateBeggar(BeggarRequestDto beggarRequestDto, String username) {
        Beggar beggar = beggarCheck(username);
        beggar.update(beggarRequestDto);
        return new ResponseEntity<>(BeggarResponseDto.of(beggar), HttpStatus.OK);
    }

//    public ResponseEntity<BeggarExpUpResponseDto> updateExp(BeggarExpUpRequestDto beggarExpUpRequestDto, String username) {
//        Beggar beggar = beggarCheck(username);
//        String nickname = beggar.getNickname();
//        Long exp = beggar.getExp() + beggarExpUpRequestDto.getExpType().getAmount();
//        Long point = beggar.getPoint() + beggarExpUpRequestDto.getExpType().getAmount();
//        Long level = beggar.getLevel();
//
//        if(beggarExpUpRequestDto.getExpType().equals(ExpType.GET_BADGE)) {
//            saveBadge(beggarExpUpRequestDto.getBadgeType(), beggar);
//        }
//
//        if (LevelType.getNextExpByLevel(level) <= exp) {
//            level ++;
//        }
//
//        BeggarExpUpResponseDto beggarExpUpResponseDto = BeggarExpUpResponseDto.builder()
//                .nickname(nickname)
//                .exp(exp)
//                .level(level)
//                .point(point)
//                .build();
//
//        beggar.updateExp(beggarExpUpResponseDto);
//        return new ResponseEntity<>(beggarExpUpResponseDto, HttpStatus.OK);
//    }

    public void updateExpNew(String username, ExpType expType) {
        Beggar beggar = beggarCheck(username);

        ////////////////////////////////////////////////
        Long plusPoint = expType.getAmount();
        /////////////////////////////////////////////////

        beggar.updatePointAndExp(plusPoint);

        /////////////////////////////////////////////////
        String pointDescription = expType.getDescription();

//        if(expType.equals(ExpType.GET_BADGE)) {
//            pointDescription = ExpType.GET_BADGE.getDescription();
//            //saveBadgeNew(beggarExpUpRequestDto.getBadgeType(), beggar);
//        }

        if(expType.equals(ExpType.BEST_SAVER)) {
            pointDescription = ExpType.BEST_SAVER.getDescription();
        }

        if(expType.equals(ExpType.LEVEL_UP)) {
            pointDescription = ExpType.LEVEL_UP.getDescription();
        }

        Point recordPoint = new Point(pointDescription, plusPoint, null, beggar);
        pointRepository.save(recordPoint);
        /////////////////////////////////////////////////

        Long exp = beggar.getExp();
        Long level = beggar.getLevel();

        if (LevelType.getNextExpByLevel(level) <= exp) {
            level++;
            beggar.updateLevel(level);
            updateExpNew(username, ExpType.LEVEL_UP); ////////////////////////////////////////////////
        }
    }
    /*
    필요한 파라미터 : ExpType expType; BadgeType badgeType;
     Beggar beggar = beggarCheck(username);
        String nickname = beggar.getNickname();
        ExpType expType = beggarExpUpRequestDto.getExpType();

        Long exp = beggar.getExp() + expType.getAmount();
        Long point = beggar.getPoint() + expType.getAmount();
        Long level = beggar.getLevel();

        String pointDescription = expType.getDescription();

        if(expType.equals(ExpType.GET_BADGE)) {
            pointDescription = ExpType.GET_BADGE.getDescription();
            saveBadge(beggarExpUpRequestDto.getBadgeType(), beggar);
        }

        if(expType.equals(ExpType.BEST_SAVER)) {
            pointDescription = ExpType.BEST_SAVER.getDescription();
        }

        if(expType.equals(ExpType.LEVEL_UP)) {
            pointDescription = ExpType.LEVEL_UP.getDescription();
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

        Point recordPoint = new Point(pointDescription, point, null, beggar);
        pointRepository.save(recordPoint);

        beggar.updateExp(beggarExpUpResponseDto);

        return new ResponseEntity<>(beggarExpUpResponseDto, HttpStatus.OK);
     */

    public void badgeCheck(User user) {
        Beggar beggar = beggarCheck(user.getUsername());
        List<ExpenditureType> badgeList = Arrays.asList(ExpenditureType.values());

        //획득 기준 통과시에
        for (ExpenditureType expenditureType : badgeList) {
            if(badgeCriteriaCheck(expenditureType, user.getId())) saveBadgeNew(expenditureType, beggar);
        }
    }

    // 해당 월에 소비 뱃지 획득 가능한지 여부
    private boolean badgeCriteriaCheck(ExpenditureType expenditureType, Long userId) {
        return switch (expenditureType){
            case UTILITY_BILL -> ledgerHistoryRepository.checkEXPType1(expenditureType, userId);
            case CONDOLENCE_EXPENSE -> ledgerHistoryRepository.checkEXPType2(expenditureType, userId);
            case TRANSPORTATION -> ledgerHistoryRepository.checkEXPType3(expenditureType, userId);
            case COMMUNICATION_EXPENSES -> ledgerHistoryRepository.checkEXPType4(expenditureType, userId);
            case INSURANCE -> ledgerHistoryRepository.checkEXPType5(expenditureType, userId);
            case EDUCATION -> ledgerHistoryRepository.checkEXPType6(expenditureType, userId);
            case SAVINGS -> ledgerHistoryRepository.checkEXPType7(expenditureType, userId);
            case CULTURE -> ledgerHistoryRepository.checkEXPType8(expenditureType, userId);
            case HEALTH -> ledgerHistoryRepository.checkEXPType9(expenditureType, userId);
            case FOOD_EXPENSES -> ledgerHistoryRepository.checkEXPType10(expenditureType, userId);
            case SHOPPING -> ledgerHistoryRepository.checkEXPType11(expenditureType, userId);
            case LEISURE_ACTIVITIES -> ledgerHistoryRepository.checkEXPType12(expenditureType, userId);
            case OTHER -> false;
        };
    }


    public void saveBadgeNew(ExpenditureType expenditureType, Beggar beggar) {
        Long badgeNum = expenditureType.getBadgeNum();
        String badgeTitle = expenditureType.getBadgeTitle();

        boolean hasBadge = beggar.getGetBadgeList().stream()
                .map(GetBadge::getBadge)
                .anyMatch(b -> b.getBadgeNum().equals(badgeNum));

        if(!hasBadge) {

            Badge badge = new Badge(badgeNum, badgeTitle);

            badgeRepository.save(badge);

            GetBadge getBadge = new GetBadge(badge, beggar);
            badge.getGetBadgeList().add(getBadge);
            getBadgeRepository.save(getBadge);

            // 뱃지 알림 주기

        } else {
            throw new IllegalArgumentException("이미 뱃지를 가지고 있습니다.");
        }
    }

//    public void saveBadge(ExpenditureType expenditureType, Beggar beggar) {
//        Long badgeNum = expenditureType.getBadgeNum();
//        String badgeTitle = expenditureType.getBadgeTitle();
//
//        boolean hasBadge = beggar.getGetBadgeList().stream()
//                .map(GetBadge::getBadge)
//                .anyMatch(b -> b.getBadgeNum().equals(badgeNum));
//
//        if(!hasBadge) {
//            Badge badge = new Badge(badgeNum, badgeTitle);
//
//            badgeRepository.save(badge);
//
//            GetBadge getBadge = new GetBadge(badge, beggar);
//            badge.getGetBadgeList().add(getBadge);
//            getBadgeRepository.save(getBadge);
//
//        } else {
//            throw new IllegalArgumentException("이미 뱃지를 가지고 있습니다.");
//        }
//    }

    public ResponseEntity<String> customBeggar(BeggarCustomRequestDto beggarCustomRequestDto, String username) {
        Beggar beggar = beggarCheck(username);
        ItemListEnum itemListEnum = beggarCustomRequestDto.getItemListEnum();
        UnWearEnum unWearEnum = beggarCustomRequestDto.getUnWearEnum();

        if(unWearEnum != null) {
            String unWear = unWearEnum.getUnWearPart();

            switch (unWear) {
                case "top" -> beggar.updateCustomTops(null);
                case "bottom" -> beggar.updateCustomBottoms(null);
                case "shoes" -> beggar.updateCustomShoes(null);
                case "acc" -> beggar.updateCustomAccessories(null);
                case "custom" -> beggar.updateCustoms(null);
                default -> throw new IllegalArgumentException("옳지 못한 행동입니다.");
            }
        } else {

            Item findItem = itemRepository.findItemByBeggar_IdAndItemNum(beggar.getId(),itemListEnum.getItemNum())
                    .orElseThrow(() -> new IllegalArgumentException("가지고 있지 않은 아이템 입니다.")
                    );

            String itemType = findItem.getItemType();

            switch (itemType) {
                case "top" -> beggar.updateCustomTops(itemListEnum);
                case "bottom" -> beggar.updateCustomBottoms(itemListEnum);
                case "shoes" -> beggar.updateCustomShoes(itemListEnum);
                case "acc" -> beggar.updateCustomAccessories(itemListEnum);
                case "custom" -> beggar.updateCustoms(itemListEnum);
                default -> throw new IllegalArgumentException("옳지 못한 행동입니다.");
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


    public void resetBadge() {
        //GetBadge만 삭제하면 되는지
        getBadgeRepository.deleteAll();
    }
}
