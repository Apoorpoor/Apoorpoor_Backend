package com.example.apoorpoor_backend.repository.challenge;

import com.example.apoorpoor_backend.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    Optional<Challenge> findChallengeBySuccessStatusIsNullAndBeggarId(Long beggarId);
    boolean existsChallengeBySuccessStatusIsNullAndBeggarId(Long beggarId);

}
