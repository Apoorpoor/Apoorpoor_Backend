package com.example.apoorpoor_backend.repository.social;

import com.example.apoorpoor_backend.model.Social;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialRepository extends JpaRepository<Social, Long> , SocialRepositoryCustom{
}
