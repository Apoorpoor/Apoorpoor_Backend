package com.example.apoorpoor_backend.dto.social;

import com.example.apoorpoor_backend.model.enumType.AccountType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RankingResponseDto {
    private Long rank_num;
    private Long beggar_id;
    private String nickname;
    private Long level;
    private String acc_url;
    private String top_url;
    private Long total;
    private AccountType accountType;
    private String date;

    @Builder
    public RankingResponseDto(Long rank_num, Long beggar_id, String nickname, Long level, String acc_url, String top_url, Long total, AccountType accountType, String date) {
        this.rank_num = rank_num;
        this.beggar_id = beggar_id;
        this.nickname = nickname;
        this.level = level;
        this.acc_url = acc_url;
        this.top_url = top_url;
        this.total = total;
        this.accountType = accountType;
        this.date = date;
    }
}
