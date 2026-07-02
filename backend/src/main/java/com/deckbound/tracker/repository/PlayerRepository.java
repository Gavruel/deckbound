package com.deckbound.tracker.repository;

import com.deckbound.tracker.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("""
        SELECT p, COUNT(m.id) as vitorias
        FROM Player p
        LEFT JOIN Match m ON m.vencedor.id = p.id
        GROUP BY p
        ORDER BY vitorias DESC
    """)
    List<Object[]> findRanking();

    Arrays findAll(UUID playgroupId);
}
