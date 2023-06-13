package com.lineate.testyourlexicon.controllers;

import com.lineate.testyourlexicon.dto.UserRegistrationDto;
import com.lineate.testyourlexicon.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.sf.saxon.type.ValidationException;
import org.springframework.http.HttpStatus;
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
  public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) throws ValidationException {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRegistrationDto));
  }

}
