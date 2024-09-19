CREATE TABLE participates
(
   id BIGSERIAL PRIMARY KEY,
   post_id BIGINT NOT NULL REFERENCES auto_post(id) ON DELETE CASCADE ,
   user_id BIGINT NOT NULL REFERENCES auto_user(id) ON DELETE CASCADE ,
   UNIQUE (post_id, user_id)
);