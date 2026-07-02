package com.deckbound.tracker.controller;

import com.deckbound.tracker.dto.request.CreateMatchRequest;
import com.deckbound.tracker.dto.response.MatchResponse;
import com.deckbound.tracker.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/playgroups/{playgroupId}/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<List<MatchResponse>> listAll(@PathVariable UUID playgroupId) {
        return ResponseEntity.ok(matchService.listAll(playgroupId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchResponse> findById(@PathVariable Long id, UUID playgroupId) {
        return ResponseEntity.ok(matchService.findById(id, playgroupId));
    }

    @PostMapping
    public ResponseEntity<MatchResponse> create(@PathVariable UUID playgroupId,
                                                @Valid @RequestBody CreateMatchRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(matchService.create(playgroupId, request));
    }

    @PatchMapping("/{id}/observacoes")
    public ResponseEntity<MatchResponse> atualizarObservacoes(
        @PathVariable Long id,
        @RequestBody Map<String, String> body
    ) {
        return ResponseEntity.ok(matchService.atualizarObservacoes(id, body.get("observacoes")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        matchService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
