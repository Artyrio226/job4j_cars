CREATE TABLE IF NOT EXISTS photo
(
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    path TEXT NOT NULL UNIQUE ,
    auto_post_id BIGINT REFERENCES auto_post(id) ON DELETE CASCADE
);