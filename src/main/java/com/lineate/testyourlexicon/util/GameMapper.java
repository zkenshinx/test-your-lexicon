package com.lineate.testyourlexicon.util;

import com.lineate.testyourlexicon.dto.GameConfigurationDto;
import com.lineate.testyourlexicon.entities.GameConfiguration;

public class GameMapper {

  private static final int DEFAULT_STEP_COUNT = 10;
  private static final int DEFAULT_STEP_TIME = 20;

  public static GameConfiguration gameConfigurationDtoToGameConfiguration(GameConfigurationDto gameConfigurationDto) {
    int numberOfSteps = DEFAULT_STEP_COUNT;
    int stepTime = DEFAULT_STEP_TIME;
    if (gameConfigurationDto.getNumberOfSteps() != null) {
      numberOfSteps = gameConfigurationDto.getNumberOfSteps();
    }
    if (gameConfigurationDto.getStepTimeInSeconds() != null) {
      stepTime = gameConfigurationDto.getStepTimeInSeconds();
    }
    return GameConfiguration.builder()
      .translateFrom(gameConfigurationDto.getTranslatedFrom())
      .translateTo(gameConfigurationDto.getTranslatedTo())
      .numberOfSteps(numberOfSteps)
      .stepTimeInSeconds(stepTime)
      .build();
  }

  public static GameConfigurationDto gameConfigurationToGameConfigurationDto(GameConfiguration gameConfiguration) {
    return GameConfigurationDto.builder()
      .translatedTo(gameConfiguration.getTranslateTo())
      .translatedFrom(gameConfiguration.getTranslateFrom())
      .numberOfSteps(gameConfiguration.getNumberOfSteps())
      .stepTimeInSeconds(gameConfiguration.getStepTimeInSeconds())
      .build();
  }

}
