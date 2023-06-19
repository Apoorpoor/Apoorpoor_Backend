package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.challenge.ChallengeRequestDto;
import com.example.apoorpoor_backend.model.enumType.ChallengeType;
import com.example.apoorpoor_backend.security.UserDetailsImpl;
import com.example.apoorpoor_backend.service.ChallengeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ChallengeController", description = "챌린지 controller")
@RestController
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;
    @PostMapping("/challenge")
    public ResponseEntity<String> createChallenge(@RequestBody ChallengeRequestDto challengeRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return challengeService.createChallenge(challengeRequestDto.getChallengeType(), userDetails.getUsername());
    }

}
