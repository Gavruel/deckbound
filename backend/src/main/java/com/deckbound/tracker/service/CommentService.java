package com.deckbound.tracker.service;

import com.deckbound.tracker.dto.request.CreateCommentRequest;
import com.deckbound.tracker.dto.response.CommentResponse;
import com.deckbound.tracker.exception.ResourceNotFoundException;
import com.deckbound.tracker.model.entity.Comment;
import com.deckbound.tracker.model.entity.Match;
import com.deckbound.tracker.model.entity.Player;
import com.deckbound.tracker.model.entity.Commander;
import com.deckbound.tracker.repository.CommentRepository;
import com.deckbound.tracker.repository.MatchRepository;
import com.deckbound.tracker.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    public List<CommentResponse> listarPorPartida(Long matchId) {
        return commentRepository.findByMatchIdWithPlayer(matchId)
            .stream()
            .map(CommentResponse::from)
            .toList();
    }

    @Transactional
    public CommentResponse criar(Long matchId, CreateCommentRequest request) {
        Match match = matchRepository.findById(matchId)
            .orElseThrow(() -> new ResourceNotFoundException("Match", matchId));

        Player player = null;
        if (request.playerId() != null) {
            player = playerRepository.findById(request.playerId())
                .orElseThrow(() -> new ResourceNotFoundException("Player", request.playerId()));
        }

        Comment comment = Comment.builder()
            .match(match)
            .player(player)
            .texto(request.texto())
            .build();

        return CommentResponse.from(commentRepository.save(comment));
    }

    @Transactional
    public void deletar(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comment", id);
        }
        commentRepository.deleteById(id);
    }
}
