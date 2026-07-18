package com.deckbound.tracker.service;

import com.deckbound.tracker.exception.PlaygroupAccessDeniedException;
import com.deckbound.tracker.repository.PlaygroupMemberRepository;
import com.deckbound.tracker.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaygroupMemberService {

    private final PlaygroupMemberRepository playgroupMemberRepository;


    @Transactional(readOnly = true)
    public void assertMember(UUID playgroupId) {
        UUID userId = CurrentUser.id();
        if (!playgroupMemberRepository.existsByUser_IdAndPlaygroup_Id(userId, playgroupId)) {
            throw new PlaygroupAccessDeniedException();
        }
    }
}
