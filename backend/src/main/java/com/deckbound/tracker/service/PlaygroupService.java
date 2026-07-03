package com.deckbound.tracker.service;

import com.deckbound.tracker.dto.request.CreateMatchRequest;
import com.deckbound.tracker.dto.response.PlayerResponse;
import com.deckbound.tracker.model.entity.Playgroup;
import com.deckbound.tracker.repository.MatchRepository;
import com.deckbound.tracker.repository.PlayerRepository;
import com.deckbound.tracker.repository.PlaygroupRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaygroupService {

    private final PlaygroupRepository playgroupRepository;
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private final MatchService matchService;

    public PlayerResponse create(CreateMatchRequest createMatchRequest) {

    }

}
