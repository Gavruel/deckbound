package com.deckbound.tracker.dto.response;

import com.deckbound.tracker.model.entity.Commander;

public record CommanderResponse(
    Long id,
    String nome,
    String imageUrl,
    String scryfallId
) {
    public static CommanderResponse from(Commander c) {
        return new CommanderResponse(c.getId(), c.getNome(), c.getImageUrl(), c.getScryfallId());
    }
}
