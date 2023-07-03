package com.lineate.testyourlexicon.controllers;

import com.lineate.testyourlexicon.dto.*;
import com.lineate.testyourlexicon.services.AuthenticationService;
import com.lineate.testyourlexicon.services.GameService;
import com.lineate.testyourlexicon.services.UserService;
import com.lineate.testyourlexicon.util.Hash;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

  private final GameService gameService;
  private final UserService userService;
  private final AuthenticationService authenticationService;

  @PostMapping
  public GameInitializedDto startGame(HttpServletRequest request) {
    return gameService.initGameForUser(getUserHash(request));
  }

  @GetMapping("/{gameId}/step")
  public StepDto nextStep(@PathVariable("gameId") Long gameId,
                          HttpServletRequest request) {
    return gameService.userActiveGameNextStep(getUserHash(request), gameId);
  }

  @PutMapping("/{gameId}/answer")
  public AnswerResponseDto answer(@PathVariable("gameId") Long gameId,
                                  @RequestBody AnswerRequestDto answerRequestDto,
                                  HttpServletRequest request) {
    return gameService.userActiveGameAnswer(getUserHash(request), answerRequestDto, gameId);
  }

  @GetMapping("/supported-languages")
  public SupportedLanguagesDto supportedLanguages() {
    return gameService.supportedLanguages();
  }

  @PutMapping("/configuration")
  public GameConfigurationDto configure(
      @RequestBody @Valid GameConfigurationDto gameConfigurationDto,
      HttpServletRequest request) {
    return gameService.configure(gameConfigurationDto, getUserHash(request));
  }

  @GetMapping("/configuration")
  public GameConfigurationDto configuration(HttpServletRequest request) {
    return gameService.userConfiguration(getUserHash(request));
  }

  private Long getUserHash(HttpServletRequest request) {
    if (authenticationService.isAuthenticated()) {
      Long id = userService
          .findUserByEmail(authenticationService.getAuthenticatedUserEmail()).getId();
      return Hash.hashToLong(id);
    }
    return Hash.hashToLong(request.getSession().getId());
  }
}
