package com.example.apoorpoor_backend.model.enumType;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum MentType {

    // 지출멘트
    MENT1("UTILITY_BILL", Arrays.asList("소중한 우리 집","이번 달 숙박비입니다","감사합니다 건물주님", "(건물)주님께 드립니다…")),// 월세+관리비+공과금
    MENT2("CONDOLENCE_EXPENSE", Arrays.asList("살다보면..", "돌려받을 금액", "위로(축하)는 돌아오는거야", "너 먼저 가는구나..", "우정 유지비")), //경조사비
    MENT3("TRANSPORTATION", Arrays.asList("뚜벅이", "베스트 드라이버", "택시타지 말고 걸으세요", "믿을 건 튼튼한 내 두 다리 뿐!")), //교통비
    MENT4("COMMUNICATION_EXPENSES", Arrays.asList("친구가 많으시네요.","알뜰 요금제 추천드려요!","2G도 빠르답니다.","라떼는 말이야…","삐삐라고 아시나요?",
            "TIP : 96k를 쓰면 더 아낄수 있습니다.", "TIP : k-999를 쓰면 더 멀리 통신가능합니다.")), //통신비
    MENT5("INSURANCE", Arrays.asList("내 몸은 내가 챙겨","언젠가는 일확천금","운전자보험은 필수")), //보험
    MENT6("EDUCATION", Arrays.asList("응원합니다!","스프링은 김영한", "사탐은 김지영", "수학은 정승제", "공무원은 에듀윌")), //교육
    MENT7("SAVINGS", Arrays.asList("티끌 모아 태산","목표 이자 100만원","화성 갈그니까")), //저축
    MENT8("CULTURE", Arrays.asList("방에서 로드뷰 여행은 어떠세요?", "홈시어터가 대세!", "OTT로 하루순삭", "다마고치로 하루 순삭")), //문화
    MENT9("HEALTH", Arrays.asList("아끼면 병든다.","치과도 한번 다녀오세요", "아프니까 청춘이다")), //건강
    MENT10("FOOD_EXPENSES", Arrays.asList("맛있었나요?", "또 먹어요..?", "맨밥의 청춘", "예비 먹방유튜버","도대체 얼마나 먹는거야 돼~지 같은넘", "멋지다~! 우리 돼지", "돼지 돼면 돼지")), //식비
    MENT11("SHOPPING", Arrays.asList("욜로라이프", "나는야 트렌드 세터", "틴트는 사지마세요 입술 깍 꺠무세요", "찢어진 청바지는 꼬매서 입으세요")),//쇼핑

    // 수입멘트
    MENT12("EMPLOYMENT_INCOME", Arrays.asList("근로소득 수입")),
    MENT13("BUSINESS", Arrays.asList("사업 수입")),
    MENT14("STOCKS", Arrays.asList("주식 수입")),
    MENT15("INVESTMENT", Arrays.asList("투자자 수입")),
    MENT16("ALLOWANCE", Arrays.asList("용돈 수입")),
    MENT17("FIXED_DEPOSIT_MATURITY", Arrays.asList("적금만기 수입")),
    MENT18("OTHER", Arrays.asList("기타 수입"));

    private final String type;
    private final List<String> ments;

    MentType(String type, List<String> ments){
        this.type = type;
        this.ments = ments;
    }

    MentType getMent(String mentName) throws Exception {
        return Arrays.stream(MentType.values())
                .filter(mentType -> mentType.type.equals(mentName))
                .findFirst()
                .orElseThrow(Exception::new);
    }
}
