package com.example.apoorpoor_backend.repository.badge;

import com.example.apoorpoor_backend.model.GetBadge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GetBadgeRepository extends JpaRepository<GetBadge, Long> , GetBadgeRepositoryCustom{
}
