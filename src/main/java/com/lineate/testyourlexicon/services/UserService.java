package com.lineate.testyourlexicon.services;

import com.lineate.testyourlexicon.dto.UserRegistrationDto;
import com.lineate.testyourlexicon.entities.User;
import com.lineate.testyourlexicon.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  private void logUserRegistration(UserRegistrationDto userRegistrationDto) {
    log.info("Registered user: {first name: {}, last name: {}, email: {}}",
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
