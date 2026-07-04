package com.deckbound.tracker.service;

import com.deckbound.tracker.dto.request.CreatePlaygroupRequest;
import com.deckbound.tracker.dto.response.PlaygroupResponse;
import com.deckbound.tracker.exception.ResourceNotFoundException;
import com.deckbound.tracker.model.entity.Playgroup;
import com.deckbound.tracker.model.entity.PlaygroupMember;
import com.deckbound.tracker.model.entity.User;
import com.deckbound.tracker.model.enums.PlaygroupMemberRole;
import com.deckbound.tracker.repository.PlaygroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.fasterxml.jackson.databind.util.ClassUtil.name;


@Service
@RequiredArgsConstructor
public class PlaygroupService {

    private final PlaygroupRepository playgroupRepository;

    @Transactional
    public PlaygroupResponse findById(UUID id) {
        Playgroup playgroup = playgroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playgroup", id));

        return PlaygroupResponse.from(playgroup);
    }


        @Transactional
        public PlaygroupResponse create(CreatePlaygroupRequest request, User user) {
            Playgroup playgroup = new Playgroup();

            playgroup.setName(request.name());
            playgroup.setInviteCode(UUID.randomUUID().toString());
            playgroup.setCreatedAt(LocalDateTime.now());
            playgroup.setCreatedBy(user);

            /*
    @Transactional
    public PlaygroupResponse create(CreatePlaygroupRequest request, User currentUser) {

        Playgroup playgroup = Playgroup.builder()
                .name(request.name())
                .inviteCode(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .createdBy(currentUser)
                .build();

        PlaygroupMember owner = PlaygroupMember.builder()
                .player(currentUser.getPlayer())
                .playgroup(playgroup)
                .playgroupMemberRole(PlaygroupMemberRole.OWNER)
                .build();

        playgroup.getMembers().add(owner);

        return PlaygroupResponse.from(playgroupRepository.save(playgroup));

    } */

    Playgroup playgroupSaved = playgroupRepository.save(playgroup);

        return PlaygroupResponse.from(playgroupSaved);
}

}
