package com.lineate.testyourlexicon.services;

import com.lineate.testyourlexicon.dto.GameConfigurationDto;
import com.lineate.testyourlexicon.entities.GameConfiguration;
import com.lineate.testyourlexicon.entities.User;
import com.lineate.testyourlexicon.exceptions.UserNotAuthenticatedException;
import com.lineate.testyourlexicon.repositories.UserRepository;
import com.lineate.testyourlexicon.util.GameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
  private final UserRepository userRepository;
  private final AuthenticationService authenticationService;

  public GameConfigurationDto configure(GameConfigurationDto gameConfigurationDto) {
    if (!authenticationService.isAuthenticated()) {
      throw new UserNotAuthenticatedException("User not authenticated");
    }
    GameConfiguration gameConfiguration =
      GameMapper.gameConfigurationDtoToGameConfiguration(gameConfigurationDto);

    User u = authenticationService.getAuthenticatedUser();
    u.setGameConfiguration(gameConfiguration);
    gameConfiguration.setUser(u);
    userRepository.save(u);

    return GameMapper.gameConfigurationToGameConfigurationDto(gameConfiguration);
  }
}
