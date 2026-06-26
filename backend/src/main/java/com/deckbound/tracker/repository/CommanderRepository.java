package com.deckbound.tracker.repository;

import com.deckbound.tracker.model.entity.Commander;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommanderRepository extends JpaRepository<Commander, Long> {

    Optional<Commander> findByScryfallId(String scryfallId);

    List<Commander> findByNomeContainingIgnoreCase(String nome);
}
