package com.example.apoorpoor_backend.repository;

import com.example.apoorpoor_backend.entity.FinancialLedger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinancialLedgerRepository extends JpaRepository<FinancialLedger, Long> {
    List<FinancialLedger> findAllByOrderByCreatedAtDesc();
    List<FinancialLedger> findAllByMemberIdAndLedgerTitleOrderByCreatedAt(String memberId, String ledgerTitle);

}
