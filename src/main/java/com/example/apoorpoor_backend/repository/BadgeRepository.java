package com.example.apoorpoor_backend.repository;

import com.example.apoorpoor_backend.model.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
