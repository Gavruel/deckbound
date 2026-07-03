package com.deckbound.tracker.dto.response;

import com.deckbound.tracker.model.entity.Playgroup;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PlaygroupResponse(
        UUID id,
        String name,
        String inviteCode,
        UserResponse createdBy,
        LocalDateTime createdAt,
        List<PlaygroupMemberResponse> members
) {
    public static PlaygroupResponse from(Playgroup playgroup) {

        List<PlaygroupMemberResponse> members =
                playgroup.getMembers() == null
                        ? List.of()
                        : playgroup.getMembers()
                        .stream()
                        .map(PlaygroupMemberResponse::from)
                        .toList();

        return  new PlaygroupResponse(
                playgroup.getId(),
                playgroup.getName(),
                playgroup.getInviteCode(),
                playgroup.getCreatedBy() != null ? UserResponse.from(playgroup.getCreatedBy()):null,
                playgroup.getCreatedAt(),
                members
        );
    }
}
