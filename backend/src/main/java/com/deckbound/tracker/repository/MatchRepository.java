package com.deckbound.tracker.repository;

import com.deckbound.tracker.model.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("""
        SELECT DISTINCT m FROM Match m
        LEFT JOIN FETCH m.vencedor
        LEFT JOIN FETCH m.jogadores mp
        LEFT JOIN FETCH mp.player
        ORDER BY m.data DESC
    """)
    List<Match> findAllWithDetails();

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
    """)
    Optional<Match> findByIdWithAllDetails(@Param("id") Long id);
}
