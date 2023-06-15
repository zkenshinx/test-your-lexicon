package com.lineate.testyourlexicon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
public class GameConfigurationDto {

  @JsonProperty("translated_from")
  @NotEmpty(message = "translated_from field must not be empty")
  private String translatedFrom;
  @JsonProperty("translated_to")
  @NotEmpty(message = "translated_to field must not be empty")
  private String translatedTo;

  @JsonProperty("number_of_steps")
  @Range(min = 1, max = 25)
  private Integer numberOfSteps;

  @JsonProperty("step_time")
  @Range(min = 5, max = 60)
  private Integer stepTimeInSeconds;
}
