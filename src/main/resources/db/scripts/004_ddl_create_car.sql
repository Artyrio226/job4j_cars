CREATE TABLE IF NOT EXISTS car
(
    id BIGSERIAL PRIMARY KEY,
    name  TEXT NOT NULL ,
    years INT NOT NULL ,
    mileage INT NOT NULL ,
    color  TEXT NOT NULL,
    body  TEXT NOT NULL,
    transmission  TEXT NOT NULL,
    engine_id BIGINT NOT NULL UNIQUE REFERENCES engine(id)
);