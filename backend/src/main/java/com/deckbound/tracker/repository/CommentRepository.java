package com.deckbound.tracker.repository;

import com.deckbound.tracker.model.entity.Commander;
import com.deckbound.tracker.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("""
        SELECT c FROM Comment c
        LEFT JOIN FETCH c.player
        WHERE c.match.id = :matchId
        ORDER BY c.createdAt ASC
    """)
    List<Comment> findByMatchIdWithPlayer(@Param("matchId") Long matchId);
}
