package com.lineate.testyourlexicon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
public class GameConfigurationDto {

  @JsonProperty("translateFrom")
  @NotEmpty(message = "translate_from field must not be empty")
  private String translateFrom;
  @JsonProperty("translateTo")
  @NotEmpty(message = "translate_to field must not be empty")
  private String translateTo;

  @JsonProperty("numberOfSteps")
  @Range(min = 1, max = 25, message = "number of steps must be between 1 and 25")
  @NotNull(message = "number_of_steps field must not be empty")
  private Integer numberOfSteps;

  @JsonProperty("stepTime")
  @Range(min = 5, max = 60, message = "number of seconds must be between 5 and 60")
  @NotNull(message = "step_time field must not be empty")
  private Integer stepTimeInSeconds;

  @JsonProperty("answerCount")
  @Range(min = 2, max = 10, message = "number of answers must be between 2 and 10")
  @NotNull(message = "answer_count field must not be empty")
  private Integer answerCount;
}
