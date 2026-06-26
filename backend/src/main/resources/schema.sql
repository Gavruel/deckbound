CREATE TABLE IF NOT EXISTS users (
    id          BIGSERIAL PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    email       VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role        VARCHAR(20)  NOT NULL DEFAULT 'PLAYER'
                    CHECK (role IN ('ADMIN', 'MODERATOR', 'PLAYER', 'VISITOR')),
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS players (
    id          BIGSERIAL PRIMARY KEY,
    nome        VARCHAR(100) NOT NULL,
    user_id     BIGINT REFERENCES users(id) ON DELETE SET NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS commanders (
    id          BIGSERIAL PRIMARY KEY,
    nome        VARCHAR(200) NOT NULL,
    image_url   VARCHAR(500),
    scryfall_id VARCHAR(100) UNIQUE,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS matches (
    id           BIGSERIAL PRIMARY KEY,
    data         TIMESTAMP    NOT NULL DEFAULT NOW(),
    match_format      VARCHAR(30)  NOT NULL DEFAULT 'COMMANDER'
                     CHECK (formato IN ('COMMANDER', 'STANDARD', 'DRAFT', 'SEALED', 'PIONEER', 'MODERN', 'LEGACY', 'VINTAGE', 'PAUPER', 'OTHER')),
    vencedor_id  BIGINT REFERENCES players(id) ON DELETE SET NULL,
    observacoes  TEXT,
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS match_players (
    id          BIGSERIAL PRIMARY KEY,
    match_id    BIGINT NOT NULL REFERENCES matches(id) ON DELETE CASCADE,
    player_id   BIGINT REFERENCES players(id) ON DELETE SET NULL,
    guest_nome  VARCHAR(100),
    CONSTRAINT chk_player_or_guest CHECK (player_id IS NOT NULL OR guest_nome IS NOT NULL)
);

CREATE TABLE IF NOT EXISTS match_player_commanders (
    id                BIGSERIAL PRIMARY KEY,
    match_player_id   BIGINT NOT NULL REFERENCES match_players(id) ON DELETE CASCADE,
    commander_id      BIGINT NOT NULL REFERENCES commanders(id) ON DELETE CASCADE,
    role              VARCHAR(20) NOT NULL DEFAULT 'COMMANDER'
                          CHECK (role IN ('COMMANDER', 'PARTNER', 'COMPANION')),
    UNIQUE (match_player_id, commander_id)
);

CREATE TABLE IF NOT EXISTS  (comments
    id          BIGSERIAL PRIMARY KEY,
    match_id    BIGINT NOT NULL REFERENCES matches(id) ON DELETE CASCADE,
    player_id   BIGINT REFERENCES players(id) ON DELETE SET NULL,
    texto       TEXT         NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_matches_vencedor    ON matches(vencedor_id);
CREATE INDEX IF NOT EXISTS idx_matches_data        ON matches(data DESC);
CREATE INDEX IF NOT EXISTS idx_match_players_match ON match_players(match_id);
CREATE INDEX IF NOT EXISTS idx_match_players_player ON match_players(player_id);
CREATE INDEX IF NOT EXISTS idx_comments_match      ON comments(match_id);
