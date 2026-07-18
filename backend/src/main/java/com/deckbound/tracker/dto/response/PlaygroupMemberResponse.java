package com.deckbound.tracker.dto.response;

import com.deckbound.tracker.model.entity.PlaygroupMember;

import java.time.LocalDateTime;
import java.util.UUID;

public record PlaygroupMemberResponse(
        UUID id,
        UserResponse user,
        String role,
        LocalDateTime joinedAt
) {
    public static  PlaygroupMemberResponse from(PlaygroupMember member) {
        return new PlaygroupMemberResponse(
                member.getId(),
                UserResponse.from(member.getUser()),
                member.getPlaygroupMemberRole().name(),
                member.getJoinedAt()
        );
    }

}
