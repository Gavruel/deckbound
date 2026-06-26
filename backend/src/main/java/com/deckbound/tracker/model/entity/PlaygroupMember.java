package com.deckbound.tracker.model.entity;

import com.deckbound.tracker.model.enums.PlaygroupMemberRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "playgroup_members", uniqueConstraints =
@UniqueConstraint(columnNames = {"player_id","playgroup_id"}))
@Builder
public class PlaygroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JoinColumn(name = "player_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Player player;

    @JoinColumn(name = "playgroup_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Playgroup playgroup;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role")
    private PlaygroupMemberRole playgroupMemberRole;

    @Column(name = "joined_at", nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    @PrePersist
    public void prePersist() {
        this.joinedAt = LocalDateTime.now();
    }
}
