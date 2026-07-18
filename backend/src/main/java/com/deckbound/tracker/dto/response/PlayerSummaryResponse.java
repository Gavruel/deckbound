package com.deckbound.tracker.dto.response;

import com.deckbound.tracker.model.entity.Player;

public record PlayerSummaryResponse(
        Long id,
        String name
) {
    public static PlayerSummaryResponse from(Player player) {
        return new PlayerSummaryResponse(
                player.getId(),
                player.getNome()
        );
    }
}
