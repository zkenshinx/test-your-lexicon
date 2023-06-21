CREATE TABLE "games" (
    game_id BIGSERIAL PRIMARY KEY,
    user_id BIGSERIAL,
    steps_left INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE "questions" (
    question_id BIGSERIAL PRIMARY KEY,
    game_id BIGSERIAL,
    translation_id BIGSERIAL,
    guessed BOOLEAN,
    FOREIGN KEY (game_id) REFERENCES games(game_id),
    FOREIGN KEY (translation_id) REFERENCES translations(id)
);