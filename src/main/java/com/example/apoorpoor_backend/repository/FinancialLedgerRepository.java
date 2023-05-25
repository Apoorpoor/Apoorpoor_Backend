package com.example.apoorpoor_backend.repository;

import com.example.apoorpoor_backend.entity.FinancialLedger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialLedgerRepository extends JpaRepository<FinancialLedger, Long> {
}
