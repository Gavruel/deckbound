package com.deckbound.tracker.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "commanders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commander {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "scryfall_id", unique = true, length = 100)
    private String scryfallId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


}
