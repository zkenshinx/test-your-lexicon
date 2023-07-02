package com.lineate.testyourlexicon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerResponseDto {

  @JsonProperty("guessed")
  private boolean guessed;
  @JsonProperty("correct_answer")
  private String correctAnswer;
  @JsonProperty("user_answer")
  private String userAnswer;

}
