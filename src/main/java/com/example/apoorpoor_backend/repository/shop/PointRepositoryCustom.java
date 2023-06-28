package com.example.apoorpoor_backend.repository.shop;

import com.example.apoorpoor_backend.dto.shop.PointResponseDto;
import com.example.apoorpoor_backend.dto.shop.PointSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PointRepositoryCustom {

    Page<PointResponseDto> findAllByPeriodAndBeggar(Long beggarId, PointSearchCondition condition, Pageable pageable);
}
