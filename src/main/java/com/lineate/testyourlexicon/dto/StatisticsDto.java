package com.lineate.testyourlexicon.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StatisticsDto {

  @JsonProperty("questionsAnswered")
  private int questionsAnswered;
  @JsonProperty("correctlyAnswered")
  private int correctlyAnswered;
  @JsonProperty("wordWithMostHits")
  private String wordWithMostHits;
  @JsonProperty("wordWithMostMisses")
  private String wordWithMostMisses;

}
