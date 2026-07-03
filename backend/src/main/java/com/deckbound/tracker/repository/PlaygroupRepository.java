package com.deckbound.tracker.repository;

import com.deckbound.tracker.model.entity.Playgroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlaygroupRepository extends JpaRepository<Playgroup, UUID> {

}
