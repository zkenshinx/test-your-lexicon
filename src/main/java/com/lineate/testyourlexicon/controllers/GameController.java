package com.lineate.testyourlexicon.controllers;

import com.lineate.testyourlexicon.dto.GameConfigurationDto;
import com.lineate.testyourlexicon.dto.SupportedLanguagesDto;
import com.lineate.testyourlexicon.security.SecurityUser;
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

  @GetMapping("/supported-languages")
  public SupportedLanguagesDto supportedLanguages() {
    return new SupportedLanguagesDto(Arrays.asList("English", "Georgian"));
  }

  @PostMapping("/configure")
  public GameConfigurationDto configure(@RequestBody @Valid GameConfigurationDto gameConfigurationDto) {
    return gameService.configure(gameConfigurationDto);
  }

  @GetMapping("/configuration")
  public GameConfigurationDto configuration() {
    return gameService.currentUserConfiguration();
  }

}
