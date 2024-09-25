CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY,
    email TEXT UNIQUE NOT NULL,
    username TEXT NOT NULL,
    password TEXT NOT NULL,
    role TEXT,
    phone_number TEXT NOT NULL
);