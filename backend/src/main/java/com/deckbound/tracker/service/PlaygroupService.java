package com.deckbound.tracker.service;

import com.deckbound.tracker.dto.request.CreatePlaygroupRequest;
import com.deckbound.tracker.dto.response.PlaygroupResponse;
import com.deckbound.tracker.exception.ResourceNotFoundException;
import com.deckbound.tracker.model.entity.Playgroup;
import com.deckbound.tracker.model.entity.User;
import com.deckbound.tracker.repository.PlaygroupRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PlaygroupService {

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private String generateInviteCode() {
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    private final PlaygroupRepository playgroupRepository;

    @Transactional(readOnly = true)
    public PlaygroupResponse getById(UUID id) {
        Playgroup playgroup = playgroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playgroup", id));

        return PlaygroupResponse.from(playgroup);
    }


    @Transactional
    public PlaygroupResponse create(CreatePlaygroupRequest request, User user) {
        Playgroup playgroup = new Playgroup();

        playgroup.setName(request.name());
        playgroup.setInviteCode(generateInviteCode());
        playgroup.setCreatedAt(LocalDateTime.now());
        //playgroup.setCreatedBy(user);

        Playgroup playgroupSaved = playgroupRepository.save(playgroup);

        return PlaygroupResponse.from(playgroupSaved);
    }

}
