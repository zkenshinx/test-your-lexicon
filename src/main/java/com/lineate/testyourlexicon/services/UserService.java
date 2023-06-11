package com.lineate.testyourlexicon.services;

import com.lineate.testyourlexicon.dto.UserDto;
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
import java.util.List;
import java.util.stream.Collectors;

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
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PASSWORD_MISMATCH");
    }

    User registeredUser = userMapper.UserRegistrationDtoToUser(userRegistrationDto);
    userRepository.save(registeredUser);

    logUserRegistration(userRegistrationDto);

    return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.UserToUserDto(registeredUser));
  }

  public List<UserDto> getAll() {
    return userRepository.findAll().stream()
      .map(UserMapper::UserToUserDto)
      .collect(Collectors.toList());
  }
}
