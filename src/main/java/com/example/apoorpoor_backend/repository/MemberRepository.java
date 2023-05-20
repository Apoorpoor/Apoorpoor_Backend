package com.example.apoorpoor_backend.repository;

import com.example.apoorpoor_backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeberRepository extends JpaRepository<Member, Long> {
}
