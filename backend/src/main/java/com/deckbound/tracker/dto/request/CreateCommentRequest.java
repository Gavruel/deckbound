package com.deckbound.tracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentRequest(
    Long playerId,

    @NotBlank(message = "Texto do comentário é obrigatório")
    @Size(min = 1, max = 2000, message = "Comentário deve ter no máximo 2000 caracteres")
    String texto
) {}
