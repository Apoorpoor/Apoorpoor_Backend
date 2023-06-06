package com.example.apoorpoor_backend.model.enumType;

import lombok.Getter;

@Getter
public enum ExpenditureType {
    UTILITY_BILL(1L,"집주인님 이번만요"),
    CONDOLENCE_EXPENSE(2L, "축의금은 얼마?"),
    TRANSPORTATION(3L, "열차 들어옵니다."),
    COMMUNICATION_EXPENSES(4L, "여보세요?"),
    INSURANCE(5L, "이달의 보험왕"),
    EDUCATION(6L, "공부의 신"),
    SAVINGS(7L, "저축의 왕"),
    CULTURE(8L, "나는 문화인"),
    HEALTH(9L, "아프면 손드세요"),
    FOOD_EXPENSES(10L, "햄 버억"),
    SHOPPING(11L, "Flex 했지 뭐얌"),
    LEISURE_ACTIVITIES(12L, "#여유 #휴식"),
    OTHER(13L, "기타");

    private final Long badgeNum;
    private final String badgeTitle;

    ExpenditureType(Long badgeNum, String badgeTitle) {
        this.badgeNum = badgeNum;
        this.badgeTitle = badgeTitle;
    }

}
