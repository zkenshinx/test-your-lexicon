package com.lineate.testyourlexicon.controllers;

import com.lineate.testyourlexicon.dto.UserRegistrationDto;
import com.lineate.testyourlexicon.services.UserRegistrationResult;
import com.lineate.testyourlexicon.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
    UserRegistrationResult userRegistrationResult = userService.createUser(userRegistrationDto);
    if (userRegistrationResult.isSuccessfulRegistration())
      return ResponseEntity.ok(userRegistrationResult.getMessage());
    return ResponseEntity.badRequest().body(userRegistrationResult.getMessage());
  }

}
