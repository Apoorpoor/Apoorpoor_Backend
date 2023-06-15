package com.example.apoorpoor_backend.dto.social;

import com.example.apoorpoor_backend.model.enumType.AccountType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
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
}
