package com.lineate.testyourlexicon.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameEndDto {

  private int correctlyAnswered;

  private int stepCount;

}
