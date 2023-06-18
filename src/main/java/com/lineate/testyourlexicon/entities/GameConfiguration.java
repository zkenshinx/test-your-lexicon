package com.lineate.testyourlexicon.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
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
  @Column(name = "user_id")
  private Long id;
  @Column(name = "translate_from")
  private String translateFrom;
  @Column(name = "translate_to")
  private String translateTo;
  @Column(name = "number_of_steps")
  private Integer numberOfSteps;
  @Column(name = "step_time")
  private Integer stepTimeInSeconds;

  @OneToOne
  @MapsId
  @JoinColumn(name = "user_id")
  private User user;
}
