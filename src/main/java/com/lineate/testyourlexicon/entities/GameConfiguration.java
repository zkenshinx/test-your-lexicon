package com.lineate.testyourlexicon.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "game_configuration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameConfiguration {

  @Id
  @Column(name = "user_hash")
  private Long userHash;
  @Column(name = "translate_from")
  private String translateFrom;
  @Column(name = "translate_to")
  private String translateTo;
  @Column(name = "number_of_steps")
  private Integer numberOfSteps;
  @Column(name = "step_time")
  private Integer stepTimeInSeconds;
  @Column(name = "answer_count")
  private Integer answerCount;

}
