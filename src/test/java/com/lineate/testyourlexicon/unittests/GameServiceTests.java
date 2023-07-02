package com.lineate.testyourlexicon.unittests;


import com.lineate.testyourlexicon.dto.*;
import com.lineate.testyourlexicon.entities.Game;
import com.lineate.testyourlexicon.entities.GameConfiguration;
import com.lineate.testyourlexicon.entities.QuestionEntity;
import com.lineate.testyourlexicon.entities.User;
import com.lineate.testyourlexicon.models.Question;
import com.lineate.testyourlexicon.repositories.GameRepository;
import com.lineate.testyourlexicon.repositories.QuestionRepository;
import com.lineate.testyourlexicon.repositories.TranslationRepository;
import com.lineate.testyourlexicon.repositories.UserRepository;
import com.lineate.testyourlexicon.services.GameService;
import com.lineate.testyourlexicon.services.TranslationService;
import com.lineate.testyourlexicon.util.GameUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GameServiceTests {

  private GameService gameService;
  private TranslationService translationService;
  private TranslationRepository translationRepository;
  private UserRepository userRepository;
  private GameRepository gameRepository;
  private QuestionRepository questionRepository;

  @BeforeEach
  public void setUp() {
    userRepository = mock(UserRepository.class);
    translationService = mock(TranslationService.class);
    translationRepository = mock(TranslationRepository.class);
    questionRepository = mock(QuestionRepository.class);
    gameRepository = mock(GameRepository.class);
    gameService = new GameService(userRepository, translationService, translationRepository,
      gameRepository, questionRepository);
  }

  @Test
  public void whenGetRandomQuestion_GetQuestion() {
    Question customQuestion =
      new Question(1L, "park", List.of("park", "notPark"));
    when(translationService.getRandomQuestion(any(), any(), anyInt()))
      .thenReturn(customQuestion);
    when(questionRepository.existsByGameAndTranslationId(any(), any()))
      .thenReturn(false);
    Question resultQuestion =
      gameService.generateRandomQuestion(GameUtil.defaultGameConfiguration(), new Game());
    assertThat(resultQuestion.getWord()).isEqualTo(customQuestion.getWord());
  }

  @Test
  public void whenGetRandomQuestion_ThereAreNoDuplicates() {
    Question customQuestion1 =
      new Question(1L, "park", List.of("park", "notPark"));
    Question customQuestion2 =
      new Question(2L, "tree", List.of("tree", "notTree"));
    when(translationService.getRandomQuestion(any(), any(), anyInt()))
      .thenReturn(customQuestion1).thenReturn(customQuestion2);
    when(questionRepository.existsByGameAndTranslationId(any(), any()))
      .thenReturn(true).thenReturn(false);
    Question resultQuestion =
      gameService.generateRandomQuestion(GameUtil.defaultGameConfiguration(), new Game());
    assertThat(resultQuestion.getWord()).isEqualTo(customQuestion2.getWord());
  }

  @Test
  public void whenSavingGameQuestionOnDatabase_ThenReturnCorrespondingQuestionEntity() {
    when(questionRepository.save(any()))
      .thenAnswer(new Answer<QuestionEntity>() {

        @Override
        public QuestionEntity answer(InvocationOnMock invocationOnMock) throws Throwable {
          return (QuestionEntity) invocationOnMock.getArgument(0);
        }
      });
    Question customQuestion =
      new Question(1L, "park", List.of("park", "notPark"));
    Game game = new Game();
    QuestionEntity questionEntity = gameService.saveQuestionInDatabase(customQuestion, game);
    assertThat(questionEntity.getGame()).isEqualTo(game);
    assertThat(questionEntity.getTranslationId()).isEqualTo(customQuestion.getTranslationId());
  }

  @Test
  public void whenUpdatingGameWithNewQuestion_GameStepsLeftDecrement() {
    when(questionRepository.save(any()))
      .thenAnswer(new Answer<Game>() {

        @Override
        public Game answer(InvocationOnMock invocationOnMock) throws Throwable {
          return (Game) invocationOnMock.getArgument(0);
        }
      });
    Game game = new Game();
    game.setStepsLeft(10);
    gameService.updateGameCurrentQuestion(game, new QuestionEntity());
    assertThat(game.getStepsLeft()).isEqualTo(9);
  }

  @Test
  public void whenUserAnswersCorrectly() {
    when(translationRepository.languageDefinitionGivenIdAndLanguage(any(), any()))
      .thenReturn("correct");
    User user = getExampleUserWithDefaultGameConfiguration();
    Game game = new Game();
    game.setGameId(1L);
    game.setUser(user);
    game.setStepsLeft(10);
    game.setCurrentQuestionId(10L);
    when(gameRepository.findById(any()))
      .thenReturn(Optional.of(game));
    when(questionRepository.findById(any()))
      .thenReturn(Optional.of(new QuestionEntity()));
    AnswerRequestDto answerRequestDto = new AnswerRequestDto();
    answerRequestDto.setAnswer("correct");

    AnswerResponseDto answerResponseDto =
         gameService.userActiveGameAnswer(user, answerRequestDto, game.getGameId());
    assertThat(answerResponseDto.isGuessed()).isTrue();
    assertThat(answerResponseDto.getUserAnswer()).isEqualTo("correct");
    assertThat(answerResponseDto.getCorrectAnswer()).isEqualTo("correct");
  }


  @Test
  public void whenUserAnswersIncorrectly() {
    when(translationRepository.languageDefinitionGivenIdAndLanguage(any(), any()))
      .thenReturn("correct");
    User user = getExampleUserWithDefaultGameConfiguration();
    Game game = new Game();
    game.setGameId(1L);
    game.setUser(user);
    game.setStepsLeft(10);
    game.setCurrentQuestionId(10L);
    when(gameRepository.findById(any()))
      .thenReturn(Optional.of(game));
    when(questionRepository.findById(any()))
      .thenReturn(Optional.of(new QuestionEntity()));
    AnswerRequestDto answerRequestDto = new AnswerRequestDto();
    answerRequestDto.setAnswer("incorrect");

    AnswerResponseDto answerResponseDto =
      gameService.userActiveGameAnswer(user, answerRequestDto, game.getGameId());
    assertThat(answerResponseDto.isGuessed()).isFalse();
    assertThat(answerResponseDto.getUserAnswer()).isEqualTo("incorrect");
    assertThat(answerResponseDto.getCorrectAnswer()).isEqualTo("correct");
  }

  @Test
  public void whenInitGameForUser_ThenGameIsCreatedInDatabase() {
    User user = getExampleUserWithDefaultGameConfiguration();
    gameService.initGameForUser(user);
    verify(gameRepository).save(any());
  }

  @Test
  public void whenUserHasAlreadyStartedGame_ExceptionIsThrown() {
    User user = getExampleUserWithDefaultGameConfiguration();
    when(gameRepository.getUserActiveGame(user)).thenReturn(Optional.of(new Game()));
    assertThatException().isThrownBy(() -> gameService.initGameForUser(user));
  }

  @Test
  public void whenChangingUserGameConfiguration_GetChangedGameConfiguration() {
    when(translationService.supportedLanguages())
      .thenReturn(Arrays.asList("japanese", "german"));
    GameConfigurationDto customGameConfigurationDto = GameConfigurationDto
        .builder()
        .translateFrom("japanese")
        .translateTo("german")
        .numberOfSteps(16)
        .stepTimeInSeconds(15)
        .answerCount(10)
        .build();
    User user = getExampleUserWithDefaultGameConfiguration();
    when(userRepository.save(user)).thenReturn(user);
    GameConfigurationDto gameConfigurationDto =
      gameService.configure(customGameConfigurationDto, user);

    assertThat(gameConfigurationDto.getTranslateFrom())
      .isEqualTo("japanese");
    assertThat(gameConfigurationDto.getTranslateTo())
      .isEqualTo("german");
    assertThat(gameConfigurationDto.getNumberOfSteps())
      .isEqualTo(16);
    assertThat(gameConfigurationDto.getStepTimeInSeconds())
      .isEqualTo(15);
    assertThat(gameConfigurationDto.getAnswerCount())
      .isEqualTo(10);


    // Check whether users game configuration has been changed
    GameConfiguration userGameConfiguration = user.getGameConfiguration();
    assertThat(userGameConfiguration.getTranslateFrom())
      .isEqualTo("japanese");
    assertThat(userGameConfiguration.getTranslateTo())
      .isEqualTo("german");
    assertThat(userGameConfiguration.getNumberOfSteps())
      .isEqualTo(16);
    assertThat(userGameConfiguration.getStepTimeInSeconds())
      .isEqualTo(15);
    assertThat(userGameConfiguration.getAnswerCount())
      .isEqualTo(10);
  }

  @Test
  public void supportedLanguagesContainsDefaultOnes() {
    when(translationService.supportedLanguages())
      .thenReturn(Arrays.asList(GameUtil.DEFAULT_TRANSLATE_FROM_LANGUAGE,
                                GameUtil.DEFAULT_TRANSLATE_TO_LANGUAGE));
    SupportedLanguagesDto supportedLanguagesDto
      = gameService.supportedLanguages();
    List<String> languages = supportedLanguagesDto.languages();
    assertThat(languages.contains(GameUtil.DEFAULT_TRANSLATE_FROM_LANGUAGE))
      .isTrue();
    assertThat(languages.contains(GameUtil.DEFAULT_TRANSLATE_TO_LANGUAGE))
      .isTrue();
  }

  @Test
  public void whenUserHasDefaultConfiguration_WeGetSameConfigurationBack() {
    User user = getExampleUserWithDefaultGameConfiguration();

    GameConfigurationDto gameConfigurationDto =
      gameService.userConfiguration(user);

    assertThat(gameConfigurationDto.getTranslateFrom())
      .isEqualTo(GameUtil.DEFAULT_TRANSLATE_FROM_LANGUAGE);
    assertThat(gameConfigurationDto.getTranslateTo())
      .isEqualTo(GameUtil.DEFAULT_TRANSLATE_TO_LANGUAGE);
    assertThat(gameConfigurationDto.getNumberOfSteps())
      .isEqualTo(GameUtil.DEFAULT_STEP_COUNT);
    assertThat(gameConfigurationDto.getStepTimeInSeconds())
      .isEqualTo(GameUtil.DEFAULT_STEP_TIME);
    assertThat(gameConfigurationDto.getAnswerCount())
      .isEqualTo(GameUtil.DEFAULT_ANSWER_COUNT);
  }

  private User getExampleUserWithDefaultGameConfiguration() {
    User user = new User();
    user.setEmail("user@gmail.com");
    user.setId(1L);
    user.setFirstName("first");
    user.setLastName("last");

    GameConfiguration gameConfiguration = GameUtil.defaultGameConfiguration();
    gameConfiguration.setUser(user);
    gameConfiguration.setId(user.getId());

    user.setGameConfiguration(gameConfiguration);
    return user;
  }

}
