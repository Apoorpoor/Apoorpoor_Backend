package com.example.apoorpoor_backend.dto.social;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.apoorpoor_backend.dto.social.QSocialResponseDto is a Querydsl Projection type for SocialResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QSocialResponseDto extends ConstructorExpression<SocialResponseDto> {

    private static final long serialVersionUID = -261895292L;

    public QSocialResponseDto(com.querydsl.core.types.Expression<Double> percent, com.querydsl.core.types.Expression<Long> expenditure, com.querydsl.core.types.Expression<Long> income, com.querydsl.core.types.Expression<Double> expenditure_avg, com.querydsl.core.types.Expression<Double> income_avg) {
        super(SocialResponseDto.class, new Class<?>[]{double.class, long.class, long.class, double.class, double.class}, percent, expenditure, income, expenditure_avg, income_avg);
    }

}

