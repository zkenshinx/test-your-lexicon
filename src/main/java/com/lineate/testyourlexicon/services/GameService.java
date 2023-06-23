package com.lineate.testyourlexicon.services;

import com.lineate.testyourlexicon.dto.*;
import com.lineate.testyourlexicon.entities.Game;
import com.lineate.testyourlexicon.entities.GameConfiguration;
import com.lineate.testyourlexicon.entities.QuestionEntity;
import com.lineate.testyourlexicon.entities.User;
import com.lineate.testyourlexicon.exceptions.GeneralMessageException;
import com.lineate.testyourlexicon.models.Question;
import com.lineate.testyourlexicon.repositories.GameRepository;
import com.lineate.testyourlexicon.repositories.QuestionRepository;
import com.lineate.testyourlexicon.repositories.TranslationRepository;
import com.lineate.testyourlexicon.repositories.UserRepository;
import com.lineate.testyourlexicon.util.GameMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {
  private final UserRepository userRepository;
  private final TranslationService translationService;
  private final TranslationRepository translationRepository;
  private final GameRepository gameRepository;
  private final QuestionRepository questionRepository;

  public GameConfigurationDto configure(GameConfigurationDto gameConfigurationDto, User user) {
    GameConfiguration gameConfiguration =
        GameMapper.gameConfigurationDtoToGameConfiguration(gameConfigurationDto);
    List<String> supportedLanguages = translationService.supportedLanguages();
    if (!supportedLanguages.contains(gameConfigurationDto.getTranslateFrom())
        || !supportedLanguages.contains(gameConfigurationDto.getTranslateTo())) {
      throw new IllegalArgumentException("unsupported language");
    }

    gameConfiguration.setId(user.getId());
    user.setGameConfiguration(gameConfiguration);
    gameConfiguration.setUser(user);
    userRepository.save(user);

    return GameMapper.gameConfigurationToGameConfigurationDto(gameConfiguration);
  }

  public GameConfigurationDto userConfiguration(User user) {
    return GameMapper.gameConfigurationToGameConfigurationDto(user.getGameConfiguration());
  }

  public SupportedLanguagesDto supportedLanguages() {
    return new SupportedLanguagesDto(translationService.supportedLanguages());
  }

  public GameInitializedDto initGameForUser(User user) {
    checkIfGameActiveForUser(user);
    Game game = new Game();
    game.setUser(user);
    game.setStepsLeft(user.getGameConfiguration().getNumberOfSteps());
    gameRepository.save(game);
    log.info("Initialized new game for user {'user': {}, 'game_id': {}}",
        user.getId(), game.getGameId());
    return new GameInitializedDto(game.getGameId());
  }

  public StepDto userActiveGameNextStep(User user, long gameId) {
    checkIfGameNotActiveForUser(user);
    Game game = gameRepository.findById(gameId)
        .orElseThrow(() -> new GeneralMessageException("Game id not valid"));
    if (game.getStepsLeft() <= 0) {
      throw new GeneralMessageException("Game has already finished");
    }
    // Get users game configuration parameters
    GameConfiguration userGameConfiguration = user.getGameConfiguration();
    String translateFrom = userGameConfiguration.getTranslateFrom();
    String translateTo = userGameConfiguration.getTranslateTo();
    int answerOptionsCount = userGameConfiguration.getAnswerCount();

    // Generate random question
    Question question = null;
    do {
      question = translationService.getRandomQuestion(translateFrom,
        translateTo, answerOptionsCount);
    } while (questionRepository.existsByGameAndTranslationId(game, question.getTranslationId()));

    // Save question in database
    QuestionEntity questionEntity = new QuestionEntity();
    questionEntity.setGuessed(false);
    questionEntity.setTranslationId(question.getTranslationId());
    questionEntity.setGame(game);
    questionEntity = questionRepository.save(questionEntity);

    game.setStepsLeft(game.getStepsLeft() - 1);
    game.setCurrentQuestionId(questionEntity.getId());
    gameRepository.save(game);

    log.info("Generated step {'user': {}, 'game_id': {}, 'question_id': {}}",
        user.getId(), game.getGameId(), questionEntity.getId());
    return StepDto.builder()
      .question(question)
      .gameId(gameId)
      .build();
  }

  public AnswerResponseDto userActiveGameAnswer(User user,
                                   AnswerRequestDto answerRequestDto,
                                   long gameId) {
    checkIfGameNotActiveForUser(user);
    Game game = gameRepository.findById(gameId)
        .orElseThrow(() -> new GeneralMessageException("Game id not valid or game has finished"));
    if (game.getStepsLeft() <= 0) {
      throw new GeneralMessageException("Game has already finished");
    }
    Long currentQuestionId = game.getCurrentQuestionId();
    if (currentQuestionId == null) {
      throw new GeneralMessageException("Couldn't find active question");
    }
    QuestionEntity questionEntity =
        questionRepository.findById(currentQuestionId).get();

    // Get correct answer
    Long translationId = questionEntity.getTranslationId();
    String language = user.getGameConfiguration().getTranslateTo();
    String correctAnswer =
         translationRepository.languageDefinitionGivenIdAndLanguage(translationId, language);

    // Check if users answer is the same as correct one
    boolean guessed = correctAnswer.equalsIgnoreCase(answerRequestDto.getAnswer());
    if (guessed) {
      questionEntity.setGuessed(true);
      questionRepository.save(questionEntity);
    }
    game.setCurrentQuestionId(null);
    gameRepository.save(game);
    log.info("User answered a step {'user': {}, 'game_id': {}, 'question': {}}",
      user.getId(), game.getGameId(), questionEntity.getId());
    return AnswerResponseDto.builder()
      .guessed(guessed)
      .correctAnswer(correctAnswer)
      .userAnswer(answerRequestDto.getAnswer())
      .build();
  }

  private void checkIfGameActiveForUser(User user) {
    if (gameRepository.getUserActiveGame(user).isPresent()) {
      throw new GeneralMessageException("A game is already started for the user");
    }
  }

  private void checkIfGameNotActiveForUser(User user) {
    if (gameRepository.getUserActiveGame(user).isEmpty()) {
      throw new GeneralMessageException("A game is not started for the user");
    }
  }
}
