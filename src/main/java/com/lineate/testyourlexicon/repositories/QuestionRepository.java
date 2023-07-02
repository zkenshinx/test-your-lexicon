package com.lineate.testyourlexicon.repositories;

import com.lineate.testyourlexicon.entities.Game;
import com.lineate.testyourlexicon.entities.QuestionEntity;
import com.lineate.testyourlexicon.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

  boolean existsByGameAndTranslationId(Game game, Long translationId);

}
