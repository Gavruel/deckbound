package com.deckbound.tracker.dto.response;


public record AuthResponse(

        String token,
        String userId,
        String email,
        String displayName
) {
}
