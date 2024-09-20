CREATE TABLE IF NOT EXISTS auto_user(
    id BIGSERIAL PRIMARY KEY,
    email TEXT UNIQUE NOT NULL,
    username TEXT NOT NULL,
    password TEXT NOT NULL,
    phone_number TEXT NOT NULL
);