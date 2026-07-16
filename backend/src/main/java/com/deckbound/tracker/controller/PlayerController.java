package com.deckbound.tracker.controller;

import com.deckbound.tracker.dto.request.CreatePlayerRequest;
import com.deckbound.tracker.dto.response.PlayerResponse;
import com.deckbound.tracker.dto.response.RankingEntryResponse;
import com.deckbound.tracker.service.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/playgroups/{playgroupId}/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<PlayerResponse>> listAll(@PathVariable UUID playgroupId) {
        return ResponseEntity.ok(playerService.listAll(playgroupId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PlayerResponse> create(
            @PathVariable UUID playgroupId,
            @Valid @RequestBody CreatePlayerRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.create(playgroupId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CreatePlayerRequest request
    ) {
        return ResponseEntity.ok(playerService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        playerService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<RankingEntryResponse>> ranking() {
        return ResponseEntity.ok(playerService.ranking());
    }
}
