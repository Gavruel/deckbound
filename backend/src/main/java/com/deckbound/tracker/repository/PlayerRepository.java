package com.deckbound.tracker.repository;

import com.deckbound.tracker.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("""
    SELECT p, COUNT(m.id)
    FROM Player p
    LEFT JOIN Match m
        ON m.vencedor = p
        AND m.playgroup.id = :playgroupId
    WHERE p.playgroup.id = :playgroupId
    GROUP BY p
    ORDER BY COUNT(m.id) DESC
""")
    List<Object[]> findRanking(@Param("playgroupId") UUID playgroupId);

    List<Player> findByPlaygroupId(UUID playgroupId);

}
