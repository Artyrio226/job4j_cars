CREATE TABLE IF NOT EXISTS auto_post
(
    id  BIGSERIAL PRIMARY KEY,
    name TEXT,
    description  TEXT NOT NULL,
    price   INT NOT NULL,
    city  TEXT NOT NULL,
    created  TIMESTAMP NOT NULL,
    is_sold  BOOLEAN,
    is_new_Car  BOOLEAN,
    users_id  BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE ,
    car_id  BIGINT NOT NULL REFERENCES car(id) ON DELETE CASCADE
);