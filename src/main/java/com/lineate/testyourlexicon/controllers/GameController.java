package com.lineate.testyourlexicon.controllers;

import com.lineate.testyourlexicon.dto.GameConfigurationDto;
import com.lineate.testyourlexicon.dto.GameInitializedDto;
import com.lineate.testyourlexicon.dto.SupportedLanguagesDto;
import com.lineate.testyourlexicon.entities.User;
import com.lineate.testyourlexicon.exceptions.UserNotAuthenticatedException;
import com.lineate.testyourlexicon.services.AuthenticationService;
import com.lineate.testyourlexicon.services.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

  private final GameService gameService;
  private final AuthenticationService authenticationService;

  @PutMapping
  public GameInitializedDto startGame() {
    return gameService.initGameForUser(getAuthenticatedUser());
  }

  @GetMapping("/supported-languages")
  public SupportedLanguagesDto supportedLanguages() {
    return gameService.supportedLanguages();
  }

  @PutMapping("/configuration")
  public GameConfigurationDto configure(
      @RequestBody @Valid GameConfigurationDto gameConfigurationDto) {
    return gameService.configure(gameConfigurationDto, getAuthenticatedUser());
  }

  @GetMapping("/configuration")
  public GameConfigurationDto configuration() {
    return gameService.userConfiguration(getAuthenticatedUser());
  }

  private User getAuthenticatedUser() {
    return authenticationService.getAuthenticatedUser()
      .orElseThrow(UserNotAuthenticatedException::new);
  }
}
