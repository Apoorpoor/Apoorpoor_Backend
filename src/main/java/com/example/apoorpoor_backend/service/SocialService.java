package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.social.*;
import com.example.apoorpoor_backend.model.Ranking;
import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.example.apoorpoor_backend.repository.social.SocialRepository;
import com.example.apoorpoor_backend.repository.user.UserRepository;
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
public class SocialService {

    private final UserRepository userRepository;
    private final SocialRepository socialRepository;

    public ResponseEntity<SocialResponseDto> getPercent(SocialSearchCondition condition, String username) {
        User findUser = userCheck(username);

        Long percent = 0L;
        Long expenditure_sum = 0L;
        Long income_sum = 0L;
        Double expenditure_avg = 0.0;
        Double income_avg = 0.0;

        if(condition.getAccountType() == AccountType.EXPENDITURE){
            expenditure_sum = socialRepository.getExpenditure(condition, findUser);
            expenditure_avg = socialRepository.getExpAverage(condition, findUser);
        }
        if(condition.getAccountType() == AccountType.INCOME){
            income_sum = socialRepository.getIncome(condition, findUser);
            income_avg = socialRepository.getIncAverage(condition, findUser);
        }

        //Long percent = socialRepository.getPercent(condition, findUser);

        SocialResponseDto socialResponseDto = SocialResponseDto.builder()
                        .percent(percent)
                        .expenditure(expenditure_sum)
                        .income(income_sum)
                        .expenditure_avg(expenditure_avg)
                        .income_avg(income_avg)
                        .build();

        return new ResponseEntity<>(socialResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<List<RankingResponseDto>> getRank(SocialSearchCondition condition) {
        List<Ranking> result = socialRepository.getRank(condition);

        List<RankingResponseDto> rankingResponseDtoList = new ArrayList<>();
        for (Ranking ranking : result) {
            rankingResponseDtoList.add(new RankingResponseDto(ranking.getRank_num(), ranking.getBeggar().getId(), ranking.getBeggar().getNickname(),
                    ranking.getTotal(), ranking.getAccountType(), ranking.getDate()));
        }
        return new ResponseEntity<>(rankingResponseDtoList, HttpStatus.OK);
    }

    public void updatePercent() {

        /*
                * -- 지출 그룹별 평균
        select
               CASE
                   WHEN U.age < 20 THEN 10
                   WHEN U.age < 30 THEN 20
                   WHEN U.age < 40 THEN 30
                   WHEN U.age < 50 THEN 40
                   WHEN U.age < 60 THEN 50
                   WHEN U.age < 70 THEN 60
                   WHEN U.age < 80 THEN 70
                   WHEN U.age < 90 THEN 80
                   WHEN U.age < 100 THEN 90
                   ELSE '0'
                   END AS age_abb,
               date_format(H.date, '%Y-%m') as date,
               COUNT(*) AS exp_count,
               sum(expenditure) AS exp_sum,
               SUM(expenditure) / COUNT(*) AS exp_avg
        from account A, users U, ledger_history H
        where 1=1
          and A.user_id = U.user_id
          and A.account_id = H.account_id
          and H.account_type = 'EXPENDITURE'
          and H.date like '2023-05%'
        group by age_abb;

        -- 수입 그룹별 평균
        select
            CASE
                WHEN U.age < 20 THEN 10
                WHEN U.age < 30 THEN 20
                WHEN U.age < 40 THEN 30
                WHEN U.age < 50 THEN 40
                WHEN U.age < 60 THEN 50
                WHEN U.age < 70 THEN 60
                WHEN U.age < 80 THEN 70
                WHEN U.age < 90 THEN 80
                WHEN U.age < 100 THEN 90
                ELSE '0'
                END AS age_abb,
            date_format(H.date, '%Y-%m') as date,
            COUNT(*) AS inc_count,
            sum(income) AS inc_sum,
            SUM(income) / COUNT(*) AS inc_avg
        from account A, users U, ledger_history H
        where 1=1
          and A.user_id = U.user_id
          and A.account_id = H.account_id
          and H.account_type = 'INCOME'
          and H.date like '2023-05%'
        group by age_abb;
                *
                *
                * */
    }

    public void updateRank() {

        // 지난달 수입 총합 업데이트

        List<IncomeTotalDto> rankIncomeSum = socialRepository.getRankIncomeSum();

        /*
        * -- 지난달 수입 총합
        select
            row_number() over(order by sum(income) desc) as rownum,
            date_format(H.date, '%Y-%m') as date,
            sum(income) AS inc_sum,
            B.beggar_id
        from account A, users U, ledger_history H, beggar B
        where 1=1
          and A.user_id = U.user_id
          and A.account_id = H.account_id
          and A.user_id = B.user_id
          and H.account_type = 'INCOME'
          and H.date like '2023-05%'
        group by beggar_id
        limit 10;

        -- 지난달 지출 총합
        select
            row_number() over(order by sum(expenditure) desc) as rownum,
            date_format(H.date, '%Y-%m') as date,
            sum(expenditure) AS exp_sum,
            B.beggar_id
        from account A, users U, ledger_history H, beggar B
        where 1=1
          and A.user_id = U.user_id
          and A.account_id = H.account_id
          and A.user_id = B.user_id
          and H.account_type = 'EXPENDITURE'
          and H.date like '2023-05%'
        group by beggar_id
        order by exp_sum desc
        limit 10;
        * */

        // 지난달 지출 총합 업데이트

        List<ExpenditureTotalDto> rankExpenditureSum = socialRepository.getRankExpenditureSum();
    }

    public User userCheck(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저 입니다.")
        );
    }

}
