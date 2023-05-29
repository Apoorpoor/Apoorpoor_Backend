package com.example.apoorpoor_backend.model;

import com.example.apoorpoor_backend.dto.AccountRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "ACCOUNT")
@Table
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<LedgerHistory> ledgerHistories = new ArrayList<>();

    public Account(AccountRequestDto requestDto, User user){
        this.title = requestDto.getTitle();
        this.user = user;
    }
    public void update(AccountRequestDto requestDto){
        this.title = requestDto.getTitle();
    }

}
