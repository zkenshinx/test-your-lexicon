package com.lineate.testyourlexicon.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class QuestionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "question_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "game_id")
  private Game game;

  @Column(name = "translation_id")
  private Long translationId;

  @Column(name = "guessed")
  private Boolean guessed;
}
