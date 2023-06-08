package com.example.apoorpoor_backend.repository.badge;

import com.example.apoorpoor_backend.model.Badge;

import java.util.List;

public interface BadgeRepositoryCustom {
    List<Badge> findByBadgeList(Long beggarId);
}
