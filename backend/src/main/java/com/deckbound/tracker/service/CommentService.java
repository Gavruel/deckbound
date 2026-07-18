package com.deckbound.tracker.service;

import com.deckbound.tracker.dto.request.CreateCommentRequest;
import com.deckbound.tracker.dto.response.CommentResponse;
import com.deckbound.tracker.exception.ResourceNotFoundException;
import com.deckbound.tracker.model.entity.Comment;
import com.deckbound.tracker.model.entity.Match;
import com.deckbound.tracker.model.entity.Player;
import com.deckbound.tracker.repository.CommentRepository;
import com.deckbound.tracker.repository.MatchRepository;
import com.deckbound.tracker.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final PlaygroupMemberService playgroupMemberService;

    @Transactional(readOnly = true)
    public List<CommentResponse> listarPorPartida(UUID playgroupId, Long matchId) {
        playgroupMemberService.assertMember(playgroupId);
        getOwnedMatch(playgroupId, matchId);

        return commentRepository.findByMatchIdWithPlayer(matchId)
            .stream()
            .map(CommentResponse::from)
            .toList();
    }

    @Transactional
    public CommentResponse criar(UUID playgroupId, Long matchId, CreateCommentRequest request) {
        playgroupMemberService.assertMember(playgroupId);
        Match match = getOwnedMatch(playgroupId, matchId);

        Player player = null;
        if (request.playerId() != null) {
            player = playerRepository.findById(request.playerId())
                .orElseThrow(() -> new ResourceNotFoundException("Player", request.playerId()));

            if (!player.getPlaygroup().getId().equals(playgroupId)) {
                throw new ResourceNotFoundException("Player", request.playerId());
            }
        }

        Comment comment = Comment.builder()
            .match(match)
            .player(player)
            .texto(request.texto())
            .build();

        return CommentResponse.from(commentRepository.save(comment));
    }

    @Transactional
    public void deletar(UUID playgroupId, Long matchId, Long commentId) {
        playgroupMemberService.assertMember(playgroupId);
        getOwnedMatch(playgroupId, matchId);

        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException("Comment", commentId));

        if (!comment.getMatch().getId().equals(matchId)) {
            throw new ResourceNotFoundException("Comment", commentId);
        }

        commentRepository.delete(comment);
    }

    /**
     * Busca o Match e garante que ele pertence ao playgroup do path.
     * Mesma lógica do getOwnedPlayer em PlayerService: 404, não 403.
     */
    private Match getOwnedMatch(UUID playgroupId, Long matchId) {
        Match match = matchRepository.findById(matchId)
            .orElseThrow(() -> new ResourceNotFoundException("Match", matchId));

        if (!match.getPlaygroup().getId().equals(playgroupId)) {
            throw new ResourceNotFoundException("Match", matchId);
        }

        return match;
    }
}
