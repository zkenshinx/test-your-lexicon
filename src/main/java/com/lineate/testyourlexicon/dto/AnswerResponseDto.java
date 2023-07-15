package com.lineate.testyourlexicon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerResponseDto {

  @JsonProperty("guessed")
  private boolean guessed;
  @JsonProperty("correctAnswer")
  private String correctAnswer;
  @JsonProperty("userAnswer")
  private String userAnswer;

}
