package com.lineate.testyourlexicon.unittests;


import com.lineate.testyourlexicon.dto.GameConfigurationDto;
import com.lineate.testyourlexicon.dto.SupportedLanguagesDto;
import com.lineate.testyourlexicon.entities.GameConfiguration;
import com.lineate.testyourlexicon.entities.User;
import com.lineate.testyourlexicon.repositories.UserRepository;
import com.lineate.testyourlexicon.services.GameService;
import com.lineate.testyourlexicon.util.GameUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GameServiceTests {

  private GameService gameService;
  private UserRepository userRepository;

  @BeforeEach
  public void setUp() {
    userRepository = mock(UserRepository.class);
    gameService = new GameService(userRepository);
  }

  @Test
  public void whenChangingUserGameConfiguration_GetChangedGameConfiguration() {
    GameConfigurationDto customGameConfigurationDto = GameConfigurationDto
        .builder()
        .translateFrom("Japanese")
        .translateTo("German")
        .numberOfSteps(16)
        .stepTimeInSeconds(15)
        .answerCount(10)
        .build();
    User user = getExampleUserWithDefaultGameConfiguration();
    when(userRepository.save(user)).thenReturn(user);
    GameConfigurationDto gameConfigurationDto =
      gameService.configure(customGameConfigurationDto, user);

    assertThat(gameConfigurationDto.getTranslateFrom())
      .isEqualTo("Japanese");
    assertThat(gameConfigurationDto.getTranslateTo())
      .isEqualTo("German");
    assertThat(gameConfigurationDto.getNumberOfSteps())
      .isEqualTo(16);
    assertThat(gameConfigurationDto.getStepTimeInSeconds())
      .isEqualTo(15);
    assertThat(gameConfigurationDto.getAnswerCount())
      .isEqualTo(10);


    // Check whether users game configuration has been changed
    GameConfiguration userGameConfiguration = user.getGameConfiguration();
    assertThat(userGameConfiguration.getTranslateFrom())
      .isEqualTo("Japanese");
    assertThat(userGameConfiguration.getTranslateTo())
      .isEqualTo("German");
    assertThat(userGameConfiguration.getNumberOfSteps())
      .isEqualTo(16);
    assertThat(userGameConfiguration.getStepTimeInSeconds())
      .isEqualTo(15);
    assertThat(userGameConfiguration.getAnswerCount())
      .isEqualTo(10);
  }

  @Test
  public void supportedLanguagesContainsDefaultOnes() {
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
