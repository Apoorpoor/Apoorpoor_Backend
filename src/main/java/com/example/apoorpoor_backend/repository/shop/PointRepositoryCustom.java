package com.example.apoorpoor_backend.repository.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.apoorpoor_backend.dto.shop.*;

public interface PointRepositoryCustom {
    Page<PointResponseDto> findAllByPeriodAndBeggar(Long beggarId, PointSearchCondition condition, Pageable pageable);
}