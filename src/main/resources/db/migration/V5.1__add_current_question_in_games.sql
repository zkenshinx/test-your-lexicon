ALTER TABLE "games"
ADD COLUMN current_question BIGINT REFERENCES questions(question_id)
