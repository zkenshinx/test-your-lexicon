CREATE TABLE "game_configuration" (
    user_id BIGSERIAL PRIMARY KEY,
    translate_from TEXT NOT NULL,
    translate_to TEXT NOT NULL,
    number_of_steps INT NOT NULL,
    step_time INT NOT NULL
);