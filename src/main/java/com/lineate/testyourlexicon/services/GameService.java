package com.lineate.testyourlexicon.services;

import com.lineate.testyourlexicon.dto.GameConfigurationDto;
import com.lineate.testyourlexicon.entities.GameConfiguration;
import com.lineate.testyourlexicon.entities.User;
import com.lineate.testyourlexicon.exceptions.UserNotAuthenticatedException;
import com.lineate.testyourlexicon.repositories.UserRepository;
import com.lineate.testyourlexicon.util.GameMapper;
import com.lineate.testyourlexicon.util.GameUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
  private final UserRepository userRepository;
  private final AuthenticationService authenticationService;

  private void checkAuthenticated() {
    if (!authenticationService.isAuthenticated()) {
      throw new UserNotAuthenticatedException("User not authenticated");
    }
  }

  public GameConfigurationDto configure(GameConfigurationDto gameConfigurationDto) {
    checkAuthenticated();
    GameConfiguration gameConfiguration =
      GameMapper.gameConfigurationDtoToGameConfiguration(gameConfigurationDto);

    User u = authenticationService.getAuthenticatedUser();
    gameConfiguration.setId(u.getId());
    u.setGameConfiguration(gameConfiguration);
    gameConfiguration.setUser(u);
    userRepository.save(u);

    return GameMapper.gameConfigurationToGameConfigurationDto(gameConfiguration);
  }

  public GameConfigurationDto currentUserConfiguration() {
    checkAuthenticated();
    GameConfiguration gc = authenticationService.getAuthenticatedUser().getGameConfiguration();
    return GameMapper.gameConfigurationToGameConfigurationDto(gc);
  }
}
