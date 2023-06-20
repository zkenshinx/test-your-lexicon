package com.lineate.testyourlexicon.util;

import com.lineate.testyourlexicon.dto.GameConfigurationDto;
import com.lineate.testyourlexicon.entities.GameConfiguration;

public class GameMapper {


  public static GameConfiguration gameConfigurationDtoToGameConfiguration(
      GameConfigurationDto gameConfigurationDto) {
    return GameConfiguration.builder()
      .translateFrom(gameConfigurationDto.getTranslateFrom().toLowerCase())
      .translateTo(gameConfigurationDto.getTranslateTo().toLowerCase())
      .numberOfSteps(gameConfigurationDto.getNumberOfSteps())
      .stepTimeInSeconds(gameConfigurationDto.getStepTimeInSeconds())
      .answerCount(gameConfigurationDto.getAnswerCount())
      .build();
  }

  public static GameConfigurationDto gameConfigurationToGameConfigurationDto(
      GameConfiguration gameConfiguration) {
    return GameConfigurationDto.builder()
      .translateTo(gameConfiguration.getTranslateTo())
      .translateFrom(gameConfiguration.getTranslateFrom())
      .numberOfSteps(gameConfiguration.getNumberOfSteps())
      .stepTimeInSeconds(gameConfiguration.getStepTimeInSeconds())
      .answerCount(gameConfiguration.getAnswerCount())
      .build();
  }

}
