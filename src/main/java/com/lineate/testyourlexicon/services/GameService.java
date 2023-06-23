package com.lineate.testyourlexicon.services;

import com.lineate.testyourlexicon.dto.GameConfigurationDto;
import com.lineate.testyourlexicon.dto.GameInitializedDto;
import com.lineate.testyourlexicon.dto.SupportedLanguagesDto;
import com.lineate.testyourlexicon.entities.Game;
import com.lineate.testyourlexicon.entities.GameConfiguration;
import com.lineate.testyourlexicon.entities.User;
import com.lineate.testyourlexicon.exceptions.GeneralMessageException;
import com.lineate.testyourlexicon.repositories.GameRepository;
import com.lineate.testyourlexicon.repositories.UserRepository;
import com.lineate.testyourlexicon.util.GameMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
  private final UserRepository userRepository;
  private final TranslationService translationService;
  private final GameRepository gameRepository;

  public GameConfigurationDto configure(GameConfigurationDto gameConfigurationDto, User user) {
    GameConfiguration gameConfiguration =
        GameMapper.gameConfigurationDtoToGameConfiguration(gameConfigurationDto);
    List<String> supportedLanguages = translationService.supportedLanguages();
    if (!supportedLanguages.contains(gameConfigurationDto.getTranslateFrom())
        || !supportedLanguages.contains(gameConfigurationDto.getTranslateTo())) {
      throw new IllegalArgumentException("unsupported language");
    }

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
    return new SupportedLanguagesDto(translationService.supportedLanguages());
  }

  public GameInitializedDto initGameForUser(User user) {
    if (gameRepository.getUserActiveGame(user).isPresent()) {
      throw new GeneralMessageException("A game is already started for the user");
    }
    Game game = new Game();
    game.setUser(user);
    game.setStepsLeft(user.getGameConfiguration().getNumberOfSteps());
    gameRepository.save(game);
    return new GameInitializedDto(game.getGameId());
  }
}
