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
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {
  private final UserRepository userRepository;
  private final TranslationService translationService;
  private final TranslationRepository translationRepository;
  private final GameRepository gameRepository;
  private final QuestionRepository questionRepository;
  private final Jedis jedis;

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

  public Question generateRandomQuestion(GameConfiguration gameConfiguration, Game game) {
    String translateFrom = gameConfiguration.getTranslateFrom();
    String translateTo = gameConfiguration.getTranslateTo();
    int answerOptionsCount = gameConfiguration.getAnswerCount();
    Question question = null;
    do {
      question = translationService.getRandomQuestion(translateFrom,
        translateTo, answerOptionsCount);
    } while (questionRepository.existsByGameAndTranslationId(game, question.getTranslationId()));
    return question;
  }

  public QuestionEntity saveQuestionInDatabase(Question question, Game game) {
    QuestionEntity questionEntity = new QuestionEntity();
    questionEntity.setGuessed(false);
    questionEntity.setTranslationId(question.getTranslationId());
    questionEntity.setGame(game);
    return questionRepository.save(questionEntity);
  }

  public Game updateGameCurrentQuestion(Game game, QuestionEntity questionEntity) {
    game.setStepsLeft(game.getStepsLeft() - 1);
    game.setCurrentQuestionId(questionEntity.getId());
    return gameRepository.save(game);
  }

  public void startStepTimeout(long gameId, int time) {
    String redisKey = String.format("game_id:step:%d", gameId);
    jedis.setex(redisKey, time, "");
  }

  public StepDto userActiveGameNextStep(User user, long gameId) {
    Game game = validateGameForUser(user, gameId);
    Question question = generateRandomQuestion(user.getGameConfiguration(), game);
    QuestionEntity questionEntity = saveQuestionInDatabase(question, game);
    final Game updatedGame = updateGameCurrentQuestion(game, questionEntity);
    log.info("Generated step {'user': {}, 'game_id': {}, 'question_id': {}}",
        user.getId(), updatedGame.getGameId(), questionEntity.getId());
    startStepTimeout(gameId, user.getGameConfiguration().getStepTimeInSeconds());
    return new StepDto(question, gameId);
  }

  public QuestionEntity getQuestion(Long questionId) {
    if (questionId == null) {
      throw new GeneralMessageException("Couldn't not find active question");
    }
    Optional<QuestionEntity> questionEntity =
        questionRepository.findById(questionId);
    if (questionEntity.isEmpty()) {
      throw new GeneralMessageException("Invalid question id");
    }
    return questionEntity.get();
  }

  @Transactional
  public AnswerResponseDto userActiveGameAnswer(User user,
                                   AnswerRequestDto answerRequestDto,
                                   long gameId) {
    Game game = validateGameForUser(user, gameId);
    // Check if game isn't timed out
    if (!jedis.exists(String.format("game_id:step:%d", gameId))) {
      throw new GeneralMessageException("Step timed out!");
    }
    QuestionEntity questionEntity = getQuestion(game.getCurrentQuestionId());

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

  private Game validateGameForUser(User user, Long gameId) {
    Game game = gameRepository.findById(gameId).orElseThrow(() -> {
      log.info("User tried to access game with invalid id "
          + "{'user': {}, 'game_id': {}}", user.getId(), gameId);
      return new GeneralMessageException("Game id not valid");
    });
    if (!game.getUser().equals(user)) {
      log.info("User tried to access game that didn't belong to him "
          + "{'user': {}, 'game_id': {}}", user.getId(), gameId);
      throw new GeneralMessageException("Given game doesn't belong to the user");
    }
    if (game.getStepsLeft() <= 0) {
      log.info("User tried to access game that was finished "
          + "{'user': {}, 'game_id': {}}", user.getId(), gameId);
      throw new GeneralMessageException("Game has already finished");
    }
    return game;
  }
}
