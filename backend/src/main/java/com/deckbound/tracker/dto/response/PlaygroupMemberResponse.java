package com.deckbound.tracker.dto.response;

import com.deckbound.tracker.model.entity.PlaygroupMember;

import java.time.LocalDateTime;
import java.util.UUID;

public record PlaygroupMemberResponse(
        UUID id,
        PlayerResponse player,
        String role,
        LocalDateTime joinedAt
) {
    public static  PlaygroupMemberResponse from(PlaygroupMember member) {
        return new PlaygroupMemberResponse(
                member.getId(),
                PlayerResponse.from(member.getPlayer()),
                member.getPlaygroupMemberRole().name(),
                member.getJoinedAt()
        );
    }

}
