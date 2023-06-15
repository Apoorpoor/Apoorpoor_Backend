package com.example.apoorpoor_backend.dto.beggar;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemDto {
    private Long itemNum;

    private String itemName;

    private Long levelLimit;

    private String itemType;
}
