package com.lineate.testyourlexicon.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Question {

  @JsonIgnore
  private Long translationId;

  @JsonProperty("word")
  private String word;

  @JsonProperty("answer_options")
  private List<String> answerOptions;

  public void addAnswer(String answer) {
    answerOptions.add(answer);
  }
}
