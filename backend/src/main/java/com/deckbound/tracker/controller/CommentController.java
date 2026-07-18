package com.deckbound.tracker.controller;

import com.deckbound.tracker.dto.request.CreateCommentRequest;
import com.deckbound.tracker.dto.response.CommentResponse;
import com.deckbound.tracker.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches/{playgroupId}/{matchId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponse>> listar(@PathVariable Long matchId) {
        return ResponseEntity.ok(commentService.listarPorPartida(matchId));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> criar(
        @PathVariable Long matchId,
        @Valid @RequestBody CreateCommentRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.criar(matchId, request));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deletar(@PathVariable Long commentId) {
        commentService.deletar(commentId);
        return ResponseEntity.noContent().build();
    }
}
