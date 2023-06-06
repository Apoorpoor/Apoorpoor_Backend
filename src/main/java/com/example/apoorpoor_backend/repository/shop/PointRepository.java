package com.example.apoorpoor_backend.repository.shop;

import com.example.apoorpoor_backend.model.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface PointRepository extends JpaRepository<Point, Long>, PointRepositoryCustom {
    //@Query("SELECT p FROM POINT p WHERE p.beggar.id = :beggarId AND p.createdAt BETWEEN :startDate AND :endDate")
    //Page<Point> findAllByPeriodAndBeggar(@Param("beggarId") Long beggarId, @Param("startDate")LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);
}
//
//@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("Beggar") Beggar beggar,
