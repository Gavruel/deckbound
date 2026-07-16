package com.deckbound.tracker.dto.response;

import java.util.UUID;

public record PlaygroupSummaryResponse(
        UUID id,
        String name
) {
}
