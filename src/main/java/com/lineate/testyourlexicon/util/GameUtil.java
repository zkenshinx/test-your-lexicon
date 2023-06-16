package com.lineate.testyourlexicon.util;

import com.lineate.testyourlexicon.entities.GameConfiguration;

public class GameUtil {

  public static final int DEFAULT_STEP_COUNT = 10;
  public static final int DEFAULT_STEP_TIME = 20;

  public static final String DEFAULT_TRANSLATE_FROM_LANGUAGE = "English";
  public static final String DEFAULT_TRANSLATE_TO_LANGUAGE = "Georgian";

  public static GameConfiguration defaultGameConfiguration() {
    return GameConfiguration.builder()
      .translateFrom(DEFAULT_TRANSLATE_FROM_LANGUAGE)
      .translateTo(DEFAULT_TRANSLATE_TO_LANGUAGE)
      .stepTimeInSeconds(DEFAULT_STEP_TIME)
      .numberOfSteps(DEFAULT_STEP_COUNT)
      .build();
  }

}
