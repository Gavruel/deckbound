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

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<PlayerResponse>> listarTodos() {
        return ResponseEntity.ok(playerService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PlayerResponse> criar(@Valid @RequestBody CreatePlayerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.criar(request));
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
