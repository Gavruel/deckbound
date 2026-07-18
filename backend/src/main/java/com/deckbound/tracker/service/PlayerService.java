package com.deckbound.tracker.service;

import com.deckbound.tracker.dto.request.CreatePlayerRequest;
import com.deckbound.tracker.dto.response.PlayerResponse;
import com.deckbound.tracker.dto.response.RankingEntryResponse;
import com.deckbound.tracker.exception.ResourceNotFoundException;
import com.deckbound.tracker.model.entity.Player;
import com.deckbound.tracker.model.entity.Playgroup;
import com.deckbound.tracker.repository.PlayerRepository;
import com.deckbound.tracker.repository.PlaygroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlaygroupRepository playgroupRepository;
    private final PlaygroupMemberService playgroupMemberService;

    @Transactional(readOnly = true)
    public List<PlayerResponse> listAll(UUID playgroupId) {
        playgroupMemberService.assertMember(playgroupId);

        return playerRepository.findByPlaygroupId(playgroupId)
                .stream()
                .map(PlayerResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PlayerResponse findById(UUID playgroupId, Long playerId) {
        playgroupMemberService.assertMember(playgroupId);

        return PlayerResponse.from(getOwnedPlayer(playgroupId, playerId));
    }

    @Transactional
    public PlayerResponse create(UUID playgroupId, CreatePlayerRequest request) {
        playgroupMemberService.assertMember(playgroupId);

        Playgroup playgroup = playgroupRepository.findById(playgroupId)
                .orElseThrow(() -> new ResourceNotFoundException("Playgroup", playgroupId));

        Player player = Player.builder()
                .nome(request.nome())
                .playgroup(playgroup)
                .build();

        return PlayerResponse.from(playerRepository.save(player));
    }

    @Transactional
    public PlayerResponse update(UUID playgroupId, Long playerId, CreatePlayerRequest request) {
        playgroupMemberService.assertMember(playgroupId);

        Player player = getOwnedPlayer(playgroupId, playerId);
        player.setNome(request.nome());

        return PlayerResponse.from(playerRepository.save(player));
    }

    @Transactional
    public void delete(UUID playgroupId, Long playerId) {
        playgroupMemberService.assertMember(playgroupId);

        Player player = getOwnedPlayer(playgroupId, playerId);
        playerRepository.delete(player);
    }

    @Transactional(readOnly = true)
    public List<RankingEntryResponse> ranking(UUID playgroupId) {
        playgroupMemberService.assertMember(playgroupId);

        return playerRepository.findRanking(playgroupId).stream()
                .map(row -> new RankingEntryResponse(
                        ((Player) row[0]).getId(),
                        ((Player) row[0]).getNome(),
                        (Long) row[1]
                ))
                .toList();
    }
    private Player getOwnedPlayer(UUID playgroupId, Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResourceNotFoundException("Player", playerId));

        if (!player.getPlaygroup().getId().equals(playgroupId)) {
            throw new ResourceNotFoundException("Player", playerId);
        }

        return player;
    }
}
