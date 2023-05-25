package com.example.apoorpoor_backend.repository;

import com.example.apoorpoor_backend.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
}
