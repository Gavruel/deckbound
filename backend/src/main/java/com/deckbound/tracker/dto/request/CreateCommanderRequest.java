package com.deckbound.tracker.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCommanderRequest(
    @NotBlank(message = "Nome é obrigatório")
    String nome,

    String imageUrl,
    String scryfallId
) {}
