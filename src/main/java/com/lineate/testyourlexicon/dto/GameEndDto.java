package com.lineate.testyourlexicon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameEndDto {

  @JsonProperty("correctlyAnswered")
  private int correctlyAnswered;
  @JsonProperty("stepCount")
  private int stepCount;

}
