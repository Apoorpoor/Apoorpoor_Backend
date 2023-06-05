package com.example.apoorpoor_backend.dto.beggar;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemNumDto {
    private Long itemNum;

    public ItemNumDto(Long itemNum) {
        this.itemNum = itemNum;
    }
}
