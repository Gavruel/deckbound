package com.deckbound.tracker.dto.response;

import com.deckbound.tracker.model.entity.Player;
import com.deckbound.tracker.model.entity.Playgroup;

import java.time.LocalDateTime;

public record PlayerResponse(
    Long id,
    PlaygroupSummaryResponse playgroup,
    String nome,
    LocalDateTime createdAt
) {
    public static PlayerResponse from(Player player) {
        return new PlayerResponse(
                player.getId(),
                new PlaygroupSummaryResponse(
                        player.getPlaygroup().getId(),
                        player.getPlaygroup().getName()
                ),
                player.getNome(),
                player.getCreatedAt()
        );
    }
}
