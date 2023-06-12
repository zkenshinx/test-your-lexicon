package com.lineate.testyourlexicon.services;

import com.lineate.testyourlexicon.dto.UserRegistrationDto;
import com.lineate.testyourlexicon.entities.User;
import com.lineate.testyourlexicon.repositories.UserRepository;
import com.lineate.testyourlexicon.util.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.saxon.type.ValidationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;

import java.sql.SQLException;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  private void logUserRegistration(UserRegistrationDto userRegistrationDto) {
    log.info("Registered user: {first name: {}, last name: {}, email: {}}",
      userRegistrationDto.getFirstName().toLowerCase(),
      userRegistrationDto.getLastName().toLowerCase(),
      userRegistrationDto.getEmail());
  }

  public ResponseEntity<?> createUser(UserRegistrationDto userRegistrationDto) throws ValidationException {
    if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmationPassword())) {
      throw new IllegalArgumentException("Passwords do not match");
    }

    User registeredUser = userMapper.UserRegistrationDtoToUser(userRegistrationDto);
    try {
      userRepository.save(registeredUser);
    } catch (DataAccessException ex) {
      throw new IllegalArgumentException("Email is already in use!");
    }

    logUserRegistration(userRegistrationDto);

    return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.UserToUserDto(registeredUser));
  }

}
