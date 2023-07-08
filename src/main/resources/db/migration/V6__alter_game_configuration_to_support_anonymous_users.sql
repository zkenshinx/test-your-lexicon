-- Change game configuration
ALTER TABLE game_configuration
RENAME COLUMN user_id TO user_hash;

-- Change games table
ALTER TABLE games
DROP CONSTRAINT games_user_id_fkey;

ALTER TABLE games
RENAME COLUMN user_id TO user_hash;