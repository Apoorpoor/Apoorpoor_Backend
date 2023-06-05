package com.example.apoorpoor_backend.model.enumType;

import lombok.Getter;

@Getter
public enum BadgeType {
    HOME(1L,"집주인님 이번만요"),
    TRIBUTE(2L, "축의금은 얼마?"),
    TRANSPORTATION(3L, "열차 들어옵니다."),
    COMMUNICATION(4L, "여보세요?"),
    INSURANCE(5L, "이달의 보험왕"),
    EDUCATION(6L, "공부의 신"),
    CULTURE(7L, "나는 문화인"),
    MEDICAL(8L, "아프면 손드세요"),
    HEALTH(9L, "3대 500"),
    FOOD(10L, "햄 버억"),
    SHOPPING(11L, "Flex 했지 뭐얌"),
    LEISURE(12L, "#여유 #휴식"),
    DEPOSIT(13L, "티끌 모아 태산");

    private final Long badgeNum;

    private final String badgeTitle;

    BadgeType(Long badgeNum, String badgeTitle) {
        this.badgeNum = badgeNum;
        this.badgeTitle = badgeTitle;
    }

}
