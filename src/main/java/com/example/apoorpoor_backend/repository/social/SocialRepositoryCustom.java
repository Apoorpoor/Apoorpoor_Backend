package com.example.apoorpoor_backend.repository.social;

import com.example.apoorpoor_backend.dto.social.*;
import com.example.apoorpoor_backend.model.Ranking;
import com.example.apoorpoor_backend.model.Social;
import com.example.apoorpoor_backend.model.User;

import java.util.List;

public interface SocialRepositoryCustom {
    Long getExpenditure(SocialSearchCondition condition, User findUser);

    Long getIncome(SocialSearchCondition condition, User findUser);

    List<Ranking> getRank(SocialSearchCondition condition);

    Long getPercent(SocialSearchCondition condition, User findUser);

    Double getExpAverage(SocialSearchCondition condition, User findUser);

    Double getIncAverage(SocialSearchCondition condition, User findUser);

    List<IncomeTotalDto> getRankIncomeSum();

    List<ExpenditureTotalDto> getRankExpenditureSum();

    List<ExpenditureAvgDto> getPercentExpenditureAvg();

    List<IncomeAvgDto> getPercentIncomeAvg();

    Social findByAgeAndDateAndGender(Long ageAbb, String date, String gender);
}
