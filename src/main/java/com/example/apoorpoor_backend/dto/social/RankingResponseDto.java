package com.example.apoorpoor_backend.dto.social;

import com.example.apoorpoor_backend.model.enumType.AccountType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RankingResponseDto {
    private Long rank_num;
    private Long beggar_id;
    private String nickname;
    private Long total;
    private AccountType accountType;
    private String date;

    @Builder
    public RankingResponseDto(Long rank_num, Long beggar_id, String nickname, Long total, AccountType accountType, String date) {
        this.rank_num = rank_num;
        this.beggar_id = beggar_id;
        this.nickname = nickname;
        this.total = total;
        this.accountType = accountType;
        this.date = date;
    }
}
