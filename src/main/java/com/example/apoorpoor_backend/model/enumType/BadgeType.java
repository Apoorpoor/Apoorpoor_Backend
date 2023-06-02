package com.example.apoorpoor_backend.model.enumType;

import lombok.Getter;

@Getter
public enum BadgeType {
    BADGE1(1L,"집주인님 이번만요"),
    BADGE2(2L, "축의금은 얼마?"),
    BADGE3(3L, "열차 들어옵니다."),
    BADGE4(4L, "여보세요?"),
    BADGE5(5L, "이달의 보험왕"),
    BADGE6(6L, "공부의 신"),
    BADGE7(7L, "나는 문화인"),
    BADGE8(8L, "아프면 손드세요"),
    BADGE9(9L, "3대 500"),
    BADGE10(10L, "햄 버억"),
    BADGE11(11L, "Flex 했지 뭐얌"),
    BADGE12(12L, "#여유 #휴식"),
    BADGE13(13L, "티끌 모아 태산");

    private final Long badgeNum;

    private final String badgeTitle;

    BadgeType(Long badgeNum, String badgeTitle) {
        this.badgeNum = badgeNum;
        this.badgeTitle = badgeTitle;
    }

}
