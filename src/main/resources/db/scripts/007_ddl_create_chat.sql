CREATE TABLE IF NOT EXISTS chat
(
    id BIGSERIAL PRIMARY KEY,
    name TEXT UNIQUE
);