package com.example.apoorpoor_backend.repository.account;

import com.example.apoorpoor_backend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
