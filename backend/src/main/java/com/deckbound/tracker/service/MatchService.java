package com.deckbound.tracker.service;

import com.deckbound.tracker.dto.request.CreateMatchRequest;
import com.deckbound.tracker.dto.response.MatchResponse;
import com.deckbound.tracker.exception.BusinessException;
import com.deckbound.tracker.exception.ResourceNotFoundException;
import com.deckbound.tracker.model.entity.*;
import com.deckbound.tracker.model.enums.MatchFormat;
import com.deckbound.tracker.model.enums.MatchPlayerCommanderRole;
import com.deckbound.tracker.repository.CommanderRepository;
import com.deckbound.tracker.repository.MatchRepository;
import com.deckbound.tracker.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final CommanderRepository commanderRepository;

    @Transactional(readOnly = true)
    public List<MatchResponse> listarTodas() {
        return matchRepository.findAllWithDetails().stream()
            .map(MatchResponse::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public MatchResponse buscarPorId(Long id) {
        Match match = matchRepository.findByIdWithAllDetails(id)
            .orElseThrow(() -> new ResourceNotFoundException("Match", id));
        return MatchResponse.from(match);
    }

    @Transactional
    public MatchResponse criar(CreateMatchRequest request) {
        // Valida número de jogadores
        if (request.jogadores().size() < 2) {
            throw new BusinessException("Uma partida precisa de ao menos 2 jogadores.");
        }
        if (request.jogadores().size() > 8) {
            throw new BusinessException("Uma partida pode ter no máximo 8 jogadores.");
        }

        // Busca o vencedor
        Player vencedor = playerRepository.findById(request.vencedorId())
            .orElseThrow(() -> new ResourceNotFoundException("Player (vencedor)", request.vencedorId()));

        // Monta a entidade Match
        MatchFormat format;
        try {
            format = MatchFormat.valueOf(request.formato().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Formato inválido: " + request.formato());
        }

        Match match = Match.builder()
            .data(request.data())
            .matchFormat(format)
            .vencedor(vencedor)
            .observacoes(request.observacoes())
            .jogadores(new LinkedHashSet<>())
            .comentarios(new LinkedHashSet<>())
            .build();

        // Monta os jogadores da partida
        boolean vencedorParticipa = false;
        for (CreateMatchRequest.MatchPlayerRequest mpReq : request.jogadores()) {
            if (mpReq.playerId() == null && (mpReq.guestNome() == null
                    || mpReq.guestNome().isBlank())) {
                throw new BusinessException("Cada jogador precisa de um playerId ou guestNome.");
            }

            MatchPlayer mp = MatchPlayer.builder()
                .match(match)
                .guestNome(mpReq.guestNome())
                .comandantes(new LinkedHashSet<>())
                .build();

            if (mpReq.playerId() != null) {
                Player player = playerRepository.findById(mpReq.playerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Player", mpReq.playerId()));
                mp.setPlayer(player);

                if (player.getId().equals(request.vencedorId())) {
                    vencedorParticipa = true;
                }
            }

            // Adiciona comandantes se houver
            if (mpReq.comandantes() != null) {
                for (CreateMatchRequest.CommanderSlotRequest slotReq : mpReq.comandantes()) {
                    MatchPlayerCommanderRole role;
                    try {
                        role = MatchPlayerCommanderRole.valueOf(slotReq.role().toUpperCase());
                    } catch (IllegalArgumentException e) {
                        throw new BusinessException("Role inválido: " + slotReq.role());
                    }

                    Commander commander = commanderRepository.findById(slotReq.commanderId())
                        .orElseThrow(() -> new ResourceNotFoundException("Commander", slotReq.commanderId()));

                    MatchPlayerCommander mpc = MatchPlayerCommander.builder()
                        .matchPlayer(mp)
                        .commander(commander)
                        .role(role)
                        .build();

                    mp.getComandantes().add(mpc);
                }
            }

            match.getJogadores().add(mp);
        }

        // Valida que o vencedor está na partida
        if (!vencedorParticipa) {
            throw new BusinessException("O vencedor informado não está na lista de jogadores da partida.");
        }

        return MatchResponse.from(matchRepository.save(match));
    }

    @Transactional
    public MatchResponse atualizarObservacoes(Long id, String observacoes) {
        Match match = matchRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Match", id));
        match.setObservacoes(observacoes);
        return MatchResponse.from(matchRepository.save(match));
    }

    @Transactional
    public void deletar(Long id) {
        if (!matchRepository.existsById(id)) {
            throw new ResourceNotFoundException("Match", id);
        }
        matchRepository.deleteById(id);
    }
}
