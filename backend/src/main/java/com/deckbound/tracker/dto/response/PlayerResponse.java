package com.deckbound.tracker.dto.response;

import com.deckbound.tracker.model.entity.Player;

import java.time.LocalDateTime;

public record PlayerResponse(
    Long id,
    String nome,
    LocalDateTime createdAt
) {
    public static PlayerResponse from(Player player) {
        return new PlayerResponse(player.getId(), player.getNome(), player.getCreatedAt());
    }
}
