package com.lineate.testyourlexicon.repositories;


import com.lineate.testyourlexicon.entities.Game;
import com.lineate.testyourlexicon.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

  public Optional<Game> getGameByUserAndStepsLeftGreaterThan(User user, Integer stepsLeft);

  public default Optional<Game> getUserActiveGame(User user) {
    return getGameByUserAndStepsLeftGreaterThan(user, 0);
  }
}
