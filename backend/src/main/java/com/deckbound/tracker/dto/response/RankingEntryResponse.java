package com.deckbound.tracker.dto.response;

public record RankingEntryResponse(
    Long playerId,
    String nome,
    Long vitorias
) {}
