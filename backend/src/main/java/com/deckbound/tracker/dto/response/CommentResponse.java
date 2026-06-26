package com.deckbound.tracker.dto.response;

import com.deckbound.tracker.model.entity.Commander;
import com.deckbound.tracker.model.entity.Comment;

import java.time.LocalDateTime;

public record CommentResponse(
    Long id,
    PlayerResponse player,
    String texto,
    LocalDateTime createdAt
) {
    public static CommentResponse from(Comment c) {
        return new CommentResponse(
            c.getId(),
            c.getPlayer() != null ? PlayerResponse.from(c.getPlayer()) : null,
            c.getTexto(),
            c.getCreatedAt()
        );
    }
}
