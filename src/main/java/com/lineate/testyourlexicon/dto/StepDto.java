package com.lineate.testyourlexicon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lineate.testyourlexicon.models.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class StepDto {

  @JsonProperty("question")
  Question question;
  @JsonProperty("game_id")
  private Long gameId;

}
