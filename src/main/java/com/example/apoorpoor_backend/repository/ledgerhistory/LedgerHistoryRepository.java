package com.example.apoorpoor_backend.repository.ledgerhistory;

import com.example.apoorpoor_backend.model.LedgerHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerHistoryRepository extends JpaRepository<LedgerHistory, Long>, LedgerHistoryRepositoryCustom{
}
