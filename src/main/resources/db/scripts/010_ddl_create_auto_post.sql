CREATE TABLE auto_post
(
    id            BIGSERIAL PRIMARY KEY,
    description   TEXT NOT NULL,
    created       TIMESTAMP NOT NULL,
    is_sold       BOOLEAN,
    auto_user_id  BIGINT NOT NULL REFERENCES auto_user (id) ON DELETE CASCADE ,
    car_id        BIGINT NOT NULL REFERENCES car(id) ON DELETE CASCADE
);