package com.lineate.testyourlexicon.controllers;

import com.lineate.testyourlexicon.dto.*;
import com.lineate.testyourlexicon.services.AuthenticationService;
import com.lineate.testyourlexicon.services.GameService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

  private final GameService gameService;
  private final AuthenticationService authenticationService;

  @PostMapping
  public GameInitializedDto startGame(HttpServletRequest request) {
    return gameService.initGameForUser(authenticationService.getUserHash(request));
  }

  @GetMapping("/{gameId}/step")
  public StepDto nextStep(@PathVariable("gameId") Long gameId,
                          HttpServletRequest request) {
    return gameService.userActiveGameNextStep(authenticationService.getUserHash(request), gameId);
  }

  @PutMapping("/{gameId}/answer")
  public AnswerResponseDto answer(@PathVariable("gameId") Long gameId,
                                  @RequestBody AnswerRequestDto answerRequestDto,
                                  HttpServletRequest request) {
    return gameService.userActiveGameAnswer(authenticationService.getUserHash(request),
                                            answerRequestDto,
                                            gameId);
  }

  @GetMapping("/{gameId}/solution")
  public CorrectAnswerDto answer(@PathVariable("gameId") Long gameId,
                                 HttpServletRequest request) {
    return gameService.getCorrectAnswer(authenticationService.getUserHash(request),
      gameId);
  }

  @PostMapping("/{gameId}/end")
  public GameEndDto endGame(@PathVariable("gameId") Long gameId,
                            HttpServletRequest request) {
    return gameService.endGame(authenticationService.getUserHash(request), gameId);
  }

  @GetMapping("/supported-languages")
  public SupportedLanguagesDto supportedLanguages() {
    return gameService.supportedLanguages();
  }

  @PutMapping("/configuration")
  public GameConfigurationDto configure(
      @RequestBody @Valid GameConfigurationDto gameConfigurationDto,
      HttpServletRequest request) {
    return gameService.configure(gameConfigurationDto, authenticationService.getUserHash(request));
  }

  @GetMapping("/configuration")
  public GameConfigurationDto configuration(HttpServletRequest request) {
    return gameService.userConfiguration(authenticationService.getUserHash(request));
  }

  @GetMapping("/achievements")
  public AchievementsDto achievements() {
    return gameService.getAchievements(authenticationService.getAuthenticatedUser().get());
  }

  @GetMapping("/statistics")
  public StatisticsDto statistics(HttpServletRequest request) {
    return gameService.getUserStatistics(authenticationService.getUserHash(request));
  }

}
