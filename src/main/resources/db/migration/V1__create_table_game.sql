CREATE TABLE game (
    game_id SERIAL PRIMARY KEY,
    is_active boolean NOT NULL DEFAULT 't',
    is_finished boolean NOT NULL DEFAULT 't',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL

);