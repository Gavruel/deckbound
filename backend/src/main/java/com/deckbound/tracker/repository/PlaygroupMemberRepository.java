package com.deckbound.tracker.repository;

import com.deckbound.tracker.model.entity.PlaygroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlaygroupMemberRepository extends JpaRepository<PlaygroupMember, UUID> {

    boolean existsByUser_IdAndPlaygroup_Id(UUID userId, UUID playgroupId);
    Optional<PlaygroupMember> findByUser_IdAndPlaygroup_Id(UUID userId, UUID playgroupId);
}
