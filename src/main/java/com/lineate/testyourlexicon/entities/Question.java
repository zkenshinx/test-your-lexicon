package com.lineate.testyourlexicon.entities;

import jakarta.persistence.*;

//@Entity
//@Table(name = "questions")
public class Question {

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
