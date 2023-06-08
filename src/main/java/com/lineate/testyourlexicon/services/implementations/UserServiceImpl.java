package com.lineate.testyourlexicon.services.implementations;

import com.lineate.testyourlexicon.dto.UserRegistrationDto;
import com.lineate.testyourlexicon.entities.User;
import com.lineate.testyourlexicon.repositories.UserRepository;
import com.lineate.testyourlexicon.services.UserRegistrationResponse;
import com.lineate.testyourlexicon.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserRegistrationResponse createUser(UserRegistrationDto userRegistrationDto) {
    if (userRepository.findUserByEmail(userRegistrationDto.getEmail()).isPresent()) {
      return new UserRegistrationResponse(false, "User with the given mail exists");
    } else if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmationPassword())) {
      return new UserRegistrationResponse(false, "Passwords do not match");
    }

    User registeredUser = new User();
    registeredUser.setFirstName(userRegistrationDto.getFirstName());
    registeredUser.setLastName(userRegistrationDto.getLastName());
    registeredUser.setEmail(userRegistrationDto.getEmail());
    String hashedPassword = passwordEncoder.encode(userRegistrationDto.getPassword());
    registeredUser.setHashedPassword(hashedPassword);

    userRepository.save(registeredUser);

    return new UserRegistrationResponse(true, "Successful registration");
  }

}
