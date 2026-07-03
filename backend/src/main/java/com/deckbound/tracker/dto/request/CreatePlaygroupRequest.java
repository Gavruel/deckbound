package com.deckbound.tracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePlaygroupRequest(

        @NotBlank(message = "Group name is required")
        @Size(min = 3, max = 80)
        String name
) {
}
