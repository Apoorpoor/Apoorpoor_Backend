package com.example.apoorpoor_backend.dto.social;

import com.example.apoorpoor_backend.model.enumType.AccountType;
import lombok.Data;

@Data
public class SocialSearchCondition {
    private AccountType accountType;
}
