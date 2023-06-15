package com.example.apoorpoor_backend.model;

import com.example.apoorpoor_backend.dto.account.AccountRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "ACCOUNT")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Account extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Balance balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<LedgerHistory> ledgerHistories = new ArrayList<>();

    public void update(AccountRequestDto requestDto){
        this.title = requestDto.getTitle();
    }

}
