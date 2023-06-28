package com.example.apoorpoor_backend.repository.beggar;

import com.example.apoorpoor_backend.model.Beggar;
import java.util.Optional;

public interface BeggarRepositoryCustom {
    Optional<Beggar> findByUsername(String username);
}