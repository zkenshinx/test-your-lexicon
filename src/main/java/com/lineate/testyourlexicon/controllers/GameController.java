package com.lineate.testyourlexicon.controllers;

import com.lineate.testyourlexicon.dto.GameConfigurationDto;
import com.lineate.testyourlexicon.dto.SupportedLanguagesDto;
import com.lineate.testyourlexicon.entities.User;
import com.lineate.testyourlexicon.exceptions.UserNotAuthenticatedException;
import com.lineate.testyourlexicon.security.SecurityUser;
import com.lineate.testyourlexicon.services.AuthenticationService;
import com.lineate.testyourlexicon.services.GameService;
import com.lineate.testyourlexicon.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Arrays;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

  private final GameService gameService;
  private final AuthenticationService authenticationService;

  @GetMapping("/supported-languages")
  public SupportedLanguagesDto supportedLanguages() {
    return new SupportedLanguagesDto(Arrays.asList("English", "Georgian"));
  }

  @PutMapping("/configuration")
  public GameConfigurationDto configure(@RequestBody @Valid GameConfigurationDto gameConfigurationDto) {
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
