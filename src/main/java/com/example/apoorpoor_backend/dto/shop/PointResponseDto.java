package com.example.apoorpoor_backend.dto.shop;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class PointResponseDto {

    private Long point_id;
    private String pointDescription;
    private Long earnedPoint;
    private Long usedPoints;
    private Long beggar_id;
    private LocalDateTime createdAt;

    @QueryProjection
    public PointResponseDto(Long point_id, String pointDescription, Long earnedPoint, Long usedPoints, Long beggar_id, LocalDateTime createdAt) {
        this.point_id = point_id;
        this.pointDescription = pointDescription;
        this.earnedPoint = earnedPoint;
        this.usedPoints = usedPoints;
        this.beggar_id = beggar_id;
        this.createdAt = createdAt;
    }
}
