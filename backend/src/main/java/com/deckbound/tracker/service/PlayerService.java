package com.deckbound.tracker.service;

import com.deckbound.tracker.dto.request.CreatePlayerRequest;
import com.deckbound.tracker.dto.response.PlayerResponse;
import com.deckbound.tracker.dto.response.RankingEntryResponse;
import com.deckbound.tracker.exception.ResourceNotFoundException;
import com.deckbound.tracker.model.entity.Player;
import com.deckbound.tracker.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    public List<PlayerResponse> listarTodos() {
        return playerRepository.findAll().stream()
            .map(PlayerResponse::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public PlayerResponse buscarPorId(Long id) {
        Player player = playerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Player", id));
        return PlayerResponse.from(player);
    }

    @Transactional
    public PlayerResponse criar(CreatePlayerRequest request) {
        Player player = Player.builder()
            .nome(request.nome())
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
    public List<RankingEntryResponse> ranking() {
        return playerRepository.findRanking().stream()
            .map(row -> new RankingEntryResponse(
                ((Player) row[0]).getId(),
                ((Player) row[0]).getNome(),
                (Long) row[1]
            ))
            .toList();
    }
}
