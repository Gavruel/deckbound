package com.deckbound.tracker.model.entity;

import com.deckbound.tracker.model.enums.MatchPlayerCommanderRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "match_player_commanders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchPlayerCommander {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_player_id", nullable = false)
    private MatchPlayer matchPlayer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commander_id", nullable = false)
    private Commander commander;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private MatchPlayerCommanderRole role;

}
