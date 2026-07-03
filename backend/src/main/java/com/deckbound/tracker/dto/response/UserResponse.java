package com.deckbound.tracker.dto.response;

import com.deckbound.tracker.model.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        String password,
        String displayName,
        LocalDateTime createAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getDisplayName(),
                user.getCreateAt()
        );
    }
}
