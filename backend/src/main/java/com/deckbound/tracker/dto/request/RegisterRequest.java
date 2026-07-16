package com.deckbound.tracker.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 6, message = "The password must be at least 6 characters long.")
        String password,

        @NotBlank
        String displayName
) {
}
