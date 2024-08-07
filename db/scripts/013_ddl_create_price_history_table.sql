CREATE TABLE price_history
(
   id BIGSERIAL PRIMARY KEY,
   before BIGINT NOT NULL,
   after BIGINT NOT NULL,
   created TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
   auto_post_id  BIGINT NOT NULL REFERENCES auto_post(id) ON DELETE CASCADE
);