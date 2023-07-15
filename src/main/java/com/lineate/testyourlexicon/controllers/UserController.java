package com.lineate.testyourlexicon.controllers;

import com.lineate.testyourlexicon.dto.UserDto;
import com.lineate.testyourlexicon.dto.UserRegistrationDto;
import com.lineate.testyourlexicon.services.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<UserDto> registerUser(@RequestBody @Valid
                                                UserRegistrationDto userRegistrationDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(userService.createUser(userRegistrationDto));
  }

  @GetMapping
  public List<UserDto> allUsers() {
    return userService.getAll();
  }

}
