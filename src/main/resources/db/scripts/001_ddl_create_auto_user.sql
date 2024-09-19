CREATE TABLE auto_user
(
    id        BIGSERIAL PRIMARY KEY,
    login     TEXT UNIQUE NOT NULL,
    password  TEXT NOT NULL
);