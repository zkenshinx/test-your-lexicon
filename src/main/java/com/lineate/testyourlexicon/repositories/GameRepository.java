package com.lineate.testyourlexicon.repositories;


import com.lineate.testyourlexicon.entities.Game;
import com.lineate.testyourlexicon.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

  Optional<Game> getGameByHashAndStepsLeftGreaterThan(Long userHash, Integer stepsLeft);

  public default Optional<Game> getUserActiveGame(Long userHash) {
    return getGameByHashAndStepsLeftGreaterThan(userHash, 0);
  }
}
