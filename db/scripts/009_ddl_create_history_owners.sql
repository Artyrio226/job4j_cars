CREATE TABLE history_owners(
    id BIGSERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL REFERENCES owner(id),
    car_id BIGINT NOT NULL REFERENCES car(id)
);