package com.lineate.testyourlexicon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class AchievementDto {
  @JsonProperty("name")
  private String name;
  @JsonProperty("description")
  private String description;
}
