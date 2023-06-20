package com.lineate.testyourlexicon.services;

import com.lineate.testyourlexicon.dto.GameConfigurationDto;
import com.lineate.testyourlexicon.dto.SupportedLanguagesDto;
import com.lineate.testyourlexicon.entities.GameConfiguration;
import com.lineate.testyourlexicon.entities.User;
import com.lineate.testyourlexicon.repositories.UserRepository;
import com.lineate.testyourlexicon.util.GameMapper;
import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
  private final UserRepository userRepository;
  private final LanguageService languageService;

  public GameConfigurationDto configure(GameConfigurationDto gameConfigurationDto, User user) {
    GameConfiguration gameConfiguration =
        GameMapper.gameConfigurationDtoToGameConfiguration(gameConfigurationDto);
    List<String> supportedLanguages = languageService.supportedLanguages();
    if (!supportedLanguages.contains(gameConfigurationDto.getTranslateFrom()))
      throw new IllegalArgumentException("unsupported language");
    if (!supportedLanguages.contains(gameConfigurationDto.getTranslateTo()))
      throw new IllegalArgumentException("unsupported language");

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
    return new SupportedLanguagesDto(languageService.supportedLanguages());
  }
}
