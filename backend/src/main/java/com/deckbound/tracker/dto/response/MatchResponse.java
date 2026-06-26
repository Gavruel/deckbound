package com.deckbound.tracker.dto.response;

import com.deckbound.tracker.model.entity.Match;
import com.deckbound.tracker.model.entity.MatchPlayer;
import com.deckbound.tracker.model.entity.MatchPlayerCommander;

import java.time.LocalDateTime;
import java.util.List;

public record MatchResponse(
    Long id,
    LocalDateTime data,
    String formato,
    PlayerResponse vencedor,
    String observacoes,
    List<MatchPlayerResponse> jogadores,
    List<CommentResponse> comentarios
) {
    public static MatchResponse from(Match match) {
        List<MatchPlayerResponse> jogadores = match.getJogadores() == null ? List.of() :
            match.getJogadores().stream().map(MatchPlayerResponse::from).toList();

        List<CommentResponse> comentarios = match.getComentarios() == null ? List.of() :
            match.getComentarios().stream().map(CommentResponse::from).toList();

        return new MatchResponse(
            match.getId(),
            match.getData(),
            match.getMatchFormat().name(),
            match.getVencedor() != null ? PlayerResponse.from(match.getVencedor()) : null,
            match.getObservacoes(),
            jogadores,
            comentarios
        );
    }

    public record MatchPlayerResponse(
        Long id,
        PlayerResponse player,
        String guestNome,
        String nomeExibido,
        List<CommanderSlotResponse> comandantes
    ) {
        public static MatchPlayerResponse from(MatchPlayer mp) {
            List<CommanderSlotResponse> comandantes = mp.getComandantes() == null ? List.of() :
                mp.getComandantes().stream().map(CommanderSlotResponse::from).toList();

            return new MatchPlayerResponse(
                mp.getId(),
                mp.getPlayer() != null ? PlayerResponse.from(mp.getPlayer()) : null,
                mp.getGuestNome(),
                mp.getNomeExibido(),
                comandantes
            );
        }
    }

    public record CommanderSlotResponse(
        Long id,
        CommanderResponse commander,
        String role
    ) {
        public static CommanderSlotResponse from(MatchPlayerCommander mpc) {
            return new CommanderSlotResponse(
                mpc.getId(),
                CommanderResponse.from(mpc.getCommander()),
                mpc.getRole().name()
            );
        }
    }
}
