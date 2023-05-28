package com.example.apoorpoor_backend.repository;


import com.example.apoorpoor_backend.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Optional<Balance> findByLedgerTitle(String ledgerTitle);
}