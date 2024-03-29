package com.lineate.testyourlexicon.repositories;

import com.lineate.testyourlexicon.entities.Game;
import com.lineate.testyourlexicon.entities.QuestionEntity;
import com.lineate.testyourlexicon.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

  boolean existsByGameAndTranslationId(Game game, Long translationId);


  @Query("SELECT COUNT(*) FROM QuestionEntity q "
      + "WHERE q.game.userHash = :userHash")
  int countNumberOfQuestionsAnsweredByUser(Long userHash);

  int countByGameAndGuessedIsTrue(Game game);

  @Query("SELECT q FROM QuestionEntity q WHERE q.game.gameId = :gameId")
  List<QuestionEntity> findAllByGameId(Long gameId);

}
