package com.deckbound.tracker.dto.request;

import io.jsonwebtoken.security.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(

        @NotBlank
        @Email
        String email,

        @NotBlank @Size(min = 6, message = "The password must be at least 6 characters long.")
        String password
) {
}
