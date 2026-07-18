package com.deckbound.tracker.service;

import com.deckbound.tracker.dto.request.CreatePlaygroupRequest;
import com.deckbound.tracker.dto.request.JoinPlaygroupRequest;
import com.deckbound.tracker.dto.response.PlaygroupResponse;
import com.deckbound.tracker.exception.AlreadyMemberException;
import com.deckbound.tracker.exception.ResourceNotFoundException;
import com.deckbound.tracker.model.entity.Playgroup;
<<<<<<< HEAD
import com.deckbound.tracker.repository.PlaygroupRepository;
import com.deckbound.tracker.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import com.deckbound.tracker.model.entity.User;
import org.springframework.transaction.annotation.Transactional;
=======
import com.deckbound.tracker.model.entity.PlaygroupMember;
import com.deckbound.tracker.model.entity.User;
import com.deckbound.tracker.model.enums.PlaygroupMemberRole;
import com.deckbound.tracker.repository.PlaygroupMemberRepository;
import com.deckbound.tracker.repository.PlaygroupRepository;
import com.deckbound.tracker.repository.UserRepository;
>>>>>>> e03404b (feat(playgroup): implement membership management and invite flow)
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaygroupService {

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int INVITE_CODE_LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();
    private final UserRepository userRepository;

    private final PlaygroupRepository playgroupRepository;
    private final UserRepository userRepository;
    private final PlaygroupMemberRepository playgroupMemberRepository;

    private String generateInviteCode() {
        String code;

        do {
            code = RANDOM.ints(INVITE_CODE_LENGTH, 0, CHARS.length()).mapToObj(CHARS::charAt).map(String::valueOf).collect(Collectors.joining());
        } while (playgroupRepository.existsByInviteCode(code));

        return code;
    }

    private User getCurrentUser() {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", userId));
    }

    @Transactional(readOnly = true)
    public PlaygroupResponse getById(UUID id) {
        Playgroup playgroup = playgroupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Playgroup", id));

        return PlaygroupResponse.from(playgroup);
    }

    @Transactional
    public PlaygroupResponse create(CreatePlaygroupRequest request) {
<<<<<<< HEAD
        Playgroup playgroup = new Playgroup();

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResourceNotFoundException("User", UUID.fromString(userId)));

=======
        User user = getCurrentUser();

        Playgroup playgroup = new Playgroup();
>>>>>>> e03404b (feat(playgroup): implement membership management and invite flow)
        playgroup.setName(request.name());
        playgroup.setInviteCode(generateInviteCode());
        playgroup.setCreatedAt(LocalDateTime.now());
        playgroup.setCreatedBy(user);

        playgroupRepository.save(playgroup);

        PlaygroupMember owner = PlaygroupMember.builder().user(user).playgroup(playgroup).playgroupMemberRole(PlaygroupMemberRole.ADMIN).build();

        playgroupMemberRepository.save(owner);

        return PlaygroupResponse.from(playgroup);
    }

    @Transactional
    public PlaygroupResponse join(JoinPlaygroupRequest request) {

        /*String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResourceNotFoundException("User", UUID.fromString(userId)));*/

        User user = getCurrentUser();

        Playgroup playgroup = playgroupRepository.findByInviteCode(request.inviteCode()).orElseThrow(() -> new ResourceNotFoundException("Playgroup", request.inviteCode()));

        if (playgroupMemberRepository.existsByUser_IdAndPlaygroup_Id(user.getId(), playgroup.getId())) {
            throw new AlreadyMemberException();
        }

        PlaygroupMember member = PlaygroupMember.builder().playgroup(playgroup).user(user).playgroupMemberRole(PlaygroupMemberRole.MEMBER).build();

        playgroupMemberRepository.save(member);

        return PlaygroupResponse.from(playgroup);
    }
}