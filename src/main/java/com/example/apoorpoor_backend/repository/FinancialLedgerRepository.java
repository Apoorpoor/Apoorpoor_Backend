package com.example.apoorpoor_backend.repository;

import com.example.apoorpoor_backend.model.FinancialLedger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FinancialLedgerRepository extends JpaRepository<FinancialLedger, Long> {
    List<FinancialLedger> findAllByOrderByCreatedAtDesc();
    List<FinancialLedger> findAllByUsernameAndLedgerTitleOrderByCreatedAt(String username, String ledgerTitle);

    Optional<FinancialLedger> findByUsernameAndLedgerTitle(String username, String ledgerTitle);

    void deleteAllByUsernameAndLedgerTitle(String username, String ledgerTitle);

}