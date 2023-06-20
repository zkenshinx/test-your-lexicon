package com.lineate.testyourlexicon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class QuestionDto {

  @JsonProperty("question")
  public String question;

  @JsonProperty("answer_options")
  public List<String> answerOptions;

  public void addAnswer(String answer) {
    answerOptions.add(answer);
  }
}
