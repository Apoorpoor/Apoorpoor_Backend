package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.social.*;
import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.Ranking;
import com.example.apoorpoor_backend.model.Social;
import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.example.apoorpoor_backend.repository.beggar.BeggarRepository;
import com.example.apoorpoor_backend.repository.social.RankingRepository;
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
    private final BeggarRepository beggarRepository;
    private final SocialRepository socialRepository;
    private final RankingRepository rankingRepository;

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

        List<Social> socialList = socialRepository.findAll();

        // 지출 그룹별 평균
        List<ExpenditureAvgDto> expenditureAvgDtoList = socialRepository.getPercentExpenditureAvg();

        for (ExpenditureAvgDto expenditureAvgDto : expenditureAvgDtoList) {
            Social social = socialRepository.findByAgeAndDateAndGender(expenditureAvgDto.getAge_abb(),
                    expenditureAvgDto.getDate(), expenditureAvgDto.getGender());
            social.updateExp(expenditureAvgDto.getDate(), expenditureAvgDto.getExp_count(),
                    expenditureAvgDto.getExp_sum(), expenditureAvgDto.getExp_avg());
        }

        // 수입 그룹별 평균
        List<IncomeAvgDto> incomeAvgDtoList = socialRepository.getPercentIncomeAvg();

        for (IncomeAvgDto incomeAvgDto : incomeAvgDtoList) {
            Social social = socialRepository.findByAgeAndDateAndGender(incomeAvgDto.getAge_abb(),
                    incomeAvgDto.getDate(), incomeAvgDto.getGender());
            social.updateInc(incomeAvgDto.getDate(), incomeAvgDto.getInc_count(),
                    incomeAvgDto.getInc_sum(), incomeAvgDto.getInc_avg());
        }

    }

    public void updateRank() {

        List<Ranking> rankingList = rankingRepository.findAll();

        // 지난달 수입 총합 업데이트
        List<IncomeTotalDto> rankIncomeSum = socialRepository.getRankIncomeSum();

        for(int i=0; i<rankIncomeSum.size(); i++){
            IncomeTotalDto incomeTotalDto = rankIncomeSum.get(i);
            Beggar beggar = beggarIdCheck(incomeTotalDto.getBeggarId());
            rankingList.get(i).updateIncomeTotal(incomeTotalDto.getDate(), incomeTotalDto.getIncSum(), beggar);
        }

        // 지난달 지출 총합 업데이트
        List<ExpenditureTotalDto> rankExpenditureSum = socialRepository.getRankExpenditureSum();

        for(int i=0; i<rankExpenditureSum.size(); i++){
            ExpenditureTotalDto expenditureTotalDto = rankExpenditureSum.get(i);
            Beggar beggar = beggarIdCheck(expenditureTotalDto.getBeggarId());
            rankingList.get(i).updateExpenditureTotal(expenditureTotalDto.getDate(), expenditureTotalDto.getExpSum(), beggar);
        }
    }

    public User userCheck(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저 입니다.")
        );
    }

    public Beggar beggarIdCheck(Long beggarId) {
        return beggarRepository.findById(beggarId).orElseThrow(
                () -> new IllegalArgumentException("푸어를 찾을 수 없습니다.")
        );
    }

}
