package com.lineate.testyourlexicon.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "games")
@Setter
@Getter
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "game_id")
  private Long gameId;
  @Column(name = "user_hash")
  private Long hash;
  @Column(name = "steps_left")
  private Integer stepsLeft;
  @Nullable
  @Column(name = "current_question")
  private Long currentQuestionId;

}
