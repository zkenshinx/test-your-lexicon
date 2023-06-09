package com.lineate.testyourlexicon.controllers;

import com.lineate.testyourlexicon.dto.UserRegistrationDto;
import com.lineate.testyourlexicon.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<Object> registerUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
    var userRegistrationResponse = userService.createUser(userRegistrationDto);
    if (userRegistrationResponse.isSuccessfulRegistration()) {
      return ResponseEntity.ok(userRegistrationResponse.getMessage());
    }
    return ResponseEntity.badRequest().body(userRegistrationResponse.getMessage());
  }

}
