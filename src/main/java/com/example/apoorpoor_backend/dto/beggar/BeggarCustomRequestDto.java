package com.example.apoorpoor_backend.dto.beggar;

import com.example.apoorpoor_backend.model.enumType.ItemListEnum;
import com.example.apoorpoor_backend.model.enumType.UnWearEnum;
import lombok.Getter;

@Getter
public class BeggarCustomRequestDto {
    private ItemListEnum itemListEnum;

    private UnWearEnum unWearEnum;

}
