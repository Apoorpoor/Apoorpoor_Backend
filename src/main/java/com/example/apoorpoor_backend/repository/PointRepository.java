package com.example.apoorpoor_backend.repository;

import com.example.apoorpoor_backend.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
}
