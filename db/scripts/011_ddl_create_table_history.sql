CREATE TABLE history
(
    id       BIGSERIAL PRIMARY KEY,
    start_at  TIMESTAMP,
    end_at    TIMESTAMP,
    owner_id BIGINT NOT NULL REFERENCES owner(id),
    car_id   BIGINT NOT NULL REFERENCES car(id)
);