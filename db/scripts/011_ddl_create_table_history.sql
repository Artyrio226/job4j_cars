CREATE TABLE history
(
    id BIGSERIAL PRIMARY KEY,
    start_at TIMESTAMP,
    end_at TIMESTAMP,
    owner_id BIGINT REFERENCES owner(id) ON DELETE CASCADE ,
    car_id BIGINT REFERENCES car(id) ON DELETE CASCADE ,
    UNIQUE (car_id, owner_id)
);