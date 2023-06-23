CREATE TABLE "games" (
     game_id BIGSERIAL PRIMARY KEY,
     user_id BIGSERIAL,
     steps_left INT,
     FOREIGN KEY (user_id) REFERENCES users(id)
);
