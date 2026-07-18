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


    @Transactional(readOnly = true)
    public List<PlayerResponse> listAll(UUID playgroupId) {
        return playerRepository.findByPlaygroupId(playgroupId)
                .stream()
                .map(PlayerResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PlayerResponse findById(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player", id));
        return PlayerResponse.from(player);
    }

    @Transactional
    public PlayerResponse create(UUID playgroupId, CreatePlayerRequest request) {
        Playgroup playgroup = playgroupRepository.findById(playgroupId)
                .orElseThrow(() -> new ResourceNotFoundException("Playgroup", playgroupId));

        Player player = Player.builder()
                .nome(request.nome())
                .playgroup(playgroup)
                .build();

        return PlayerResponse.from(playerRepository.save(player));
    }

    @Transactional
    public PlayerResponse atualizar(Long id, CreatePlayerRequest request) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player", id));
        player.setNome(request.nome());
        return PlayerResponse.from(playerRepository.save(player));
    }

    @Transactional
    public void deletar(Long id) {
        if (!playerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Player", id);
        }
        playerRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<RankingEntryResponse> ranking(UUID playgroupId) {
        return playerRepository.findRanking(playgroupId).stream()
                .map(row -> new RankingEntryResponse(
                        ((Player) row[0]).getId(),
                        ((Player) row[0]).getNome(),
                        (Long) row[1]
                ))
                .toList();
    }
}
