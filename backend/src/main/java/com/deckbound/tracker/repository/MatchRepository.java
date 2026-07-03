package com.deckbound.tracker.repository;

import com.deckbound.tracker.model.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("""
        SELECT DISTINCT m FROM Match m
        LEFT JOIN FETCH m.vencedor
        LEFT JOIN FETCH m.jogadores mp
        LEFT JOIN FETCH mp.player
        WHERE m.playgroup.id = :playgroupId
        ORDER BY m.data DESC
    """)
    List<Match> findAllWithDetails(@Param("playgroupId") UUID playgroupId);

    @Query("""
        SELECT DISTINCT m FROM Match m
        LEFT JOIN FETCH m.vencedor
        LEFT JOIN FETCH m.jogadores mp
        LEFT JOIN FETCH mp.player
        LEFT JOIN FETCH mp.comandantes mpc
        LEFT JOIN FETCH mpc.commander
        LEFT JOIN FETCH m.comentarios c
        LEFT JOIN FETCH c.player
        WHERE m.id = :id
        AND m.playgroup.id = :playgroupId
    """)
    Optional<Match> findByIdWithAllDetails(@Param("id") Long id,@Param("playgroupId") UUID playgroupId);

    Optional<Match> findByIdAndPlaygroup_Id(Long id, UUID playgroupId);
}
