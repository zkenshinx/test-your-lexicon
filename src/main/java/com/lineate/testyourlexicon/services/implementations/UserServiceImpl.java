package com.lineate.testyourlexicon.services.implementations;

import com.lineate.testyourlexicon.dto.UserRegistrationDto;
import com.lineate.testyourlexicon.entities.User;
import com.lineate.testyourlexicon.repositories.UserRepository;
import com.lineate.testyourlexicon.services.UserRegistrationResponse;
import com.lineate.testyourlexicon.services.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  private void logUserRegistration(UserRegistrationDto userRegistrationDto) {
    logger.info("Registered user: {first name: {}, last name: {}, email: {}}",
      userRegistrationDto.getFirstName().toLowerCase(),
      userRegistrationDto.getLastName().toLowerCase(),
      userRegistrationDto.getEmail());
  }

  public UserRegistrationResponse createUser(UserRegistrationDto userRegistrationDto) {
    if (userRepository.findUserByEmail(userRegistrationDto.getEmail()).isPresent()) {
      return new UserRegistrationResponse(false, "User with the given mail exists");
    } else if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmationPassword())) {
      return new UserRegistrationResponse(false, "Passwords do not match");
    }

    User registeredUser = new User();
    registeredUser.setFirstName(userRegistrationDto.getFirstName().toLowerCase());
    registeredUser.setLastName(userRegistrationDto.getLastName().toLowerCase());
    registeredUser.setEmail(userRegistrationDto.getEmail());
    String hashedPassword = passwordEncoder.encode(userRegistrationDto.getPassword());
    registeredUser.setHashedPassword(hashedPassword);

    userRepository.save(registeredUser);

    logUserRegistration(userRegistrationDto);

    return new UserRegistrationResponse(true, "Successful registration");
  }

}
