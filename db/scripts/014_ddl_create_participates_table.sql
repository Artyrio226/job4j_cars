CREATE TABLE participates
(
   id BIGSERIAL PRIMARY KEY,
   post_id BIGINT NOT NULL REFERENCES auto_post(id),
   user_id BIGINT NOT NULL REFERENCES auto_user(id),
   UNIQUE (post_id, user_id)
);