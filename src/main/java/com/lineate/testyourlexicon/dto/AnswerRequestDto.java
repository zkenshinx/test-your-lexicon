package com.lineate.testyourlexicon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AnswerRequestDto {

  @JsonProperty("answer")
  private String answer;

}
