package com.lineate.testyourlexicon.services;

import com.lineate.testyourlexicon.dto.GameConfigurationDto;
import com.lineate.testyourlexicon.dto.SupportedLanguagesDto;
import com.lineate.testyourlexicon.entities.GameConfiguration;
import com.lineate.testyourlexicon.entities.User;
import com.lineate.testyourlexicon.exceptions.UserNotAuthenticatedException;
import com.lineate.testyourlexicon.repositories.UserRepository;
import com.lineate.testyourlexicon.util.GameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class GameService {
  private final UserRepository userRepository;

  public GameConfigurationDto configure(GameConfigurationDto gameConfigurationDto, User user) {
    GameConfiguration gameConfiguration =
      GameMapper.gameConfigurationDtoToGameConfiguration(gameConfigurationDto);

    gameConfiguration.setId(user.getId());
    user.setGameConfiguration(gameConfiguration);
    gameConfiguration.setUser(user);
    userRepository.save(user);

    return GameMapper.gameConfigurationToGameConfigurationDto(gameConfiguration);
  }

  public GameConfigurationDto userConfiguration(User user) {
    return GameMapper.gameConfigurationToGameConfigurationDto(user.getGameConfiguration());
  }

  public SupportedLanguagesDto supportedLanguages() {
    return new SupportedLanguagesDto(Arrays.asList("English", "Georgian"));
  }
}
