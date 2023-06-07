package com.example.apoorpoor_backend.model.enumType;

import lombok.Getter;

@Getter
public enum ExpenditureType {
    UTILITY_BILL(1L,"집주인님 이번만요", "exclude"),
    CONDOLENCE_EXPENSE(2L, "축의금은 얼마?", "badge_tribute.svg"),
    TRANSPORTATION(3L, "열차 들어옵니다.", "badge_transportation.svg"),
    COMMUNICATION_EXPENSES(4L, "여보세요?", "badge_communication.svg"),
    INSURANCE(5L, "이달의 보험왕", "badge_insurance.svg"),
    EDUCATION(6L, "공부의 신", "exclude"),
    SAVINGS(7L, "저축의 왕", "badge_deposit.svg"),
    CULTURE(8L, "나는 문화인", "badge_culture.svg"),
    HEALTH(9L, "아프면 손드세요", "badge_medical.svg"),
    FOOD_EXPENSES(10L, "햄 버억", "badge_food.svg"),
    SHOPPING(11L, "Flex 했지 뭐얌", "badge_shopping.svg"),
    LEISURE_ACTIVITIES(12L, "#여유 #휴식", "badge_leisure.svg"),
    OTHER(13L, "기타", "exclude");

    private final Long badgeNum;
    private final String badgeTitle;
    private final String badgeImage;

    ExpenditureType(Long badgeNum, String badgeTitle, String badgeImage) {
        this.badgeNum = badgeNum;
        this.badgeTitle = badgeTitle;
        this.badgeImage = badgeImage;
    }

}
