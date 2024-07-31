CREATE TABLE auto_post
(
    id            BIGSERIAL PRIMARY KEY,
    description   TEXT                           NOT NULL,
    created       TIMESTAMP                      NOT NULL,
    is_sold       BOOLEAN,
    auto_user_id  BIGINT REFERENCES auto_user (id)  NOT NULL,
    car_id        BIGINT REFERENCES car(id)         NOT NULL
);