package com.deckbound.tracker.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public record CreateMatchRequest(
    LocalDateTime data,

    @NotNull(message = "Formato é obrigatório")
    String formato,

    @NotNull(message = "Vencedor é obrigatório")
    Long vencedorId,

    String observacoes,

    @NotNull(message = "Lista de jogadores é obrigatória")
    @Size(min = 2, message = "Uma partida precisa de ao menos 2 jogadores")
    @Valid
    List<MatchPlayerRequest> jogadores
) {
    public record MatchPlayerRequest(
        Long playerId,
        String guestNome,
        List<CommanderSlotRequest> comandantes
    ) {}

    public record CommanderSlotRequest(
        @NotNull(message = "Commander ID é obrigatório")
        Long commanderId,

        @NotNull(message = "Role do comandante é obrigatório")
        String role
    ) {}
}
