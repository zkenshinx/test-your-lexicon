package com.lineate.testyourlexicon.controllers;

import com.lineate.testyourlexicon.dto.UserRegistrationDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @PostMapping("/register")
  public UserRegistrationDto registerUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
    return userRegistrationDto;
  }

}
